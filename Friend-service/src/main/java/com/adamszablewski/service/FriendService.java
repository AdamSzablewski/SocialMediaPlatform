package com.adamszablewski.service;

import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendList;
import com.adamszablewski.classes.FriendRequest;
import com.adamszablewski.classes.FriendRequestStatus;
import com.adamszablewski.dtos.FriendDto;
import com.adamszablewski.dtos.FriendListDto;
import com.adamszablewski.exceptions.NoFriendRequestException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.FriendListRepository;
import com.adamszablewski.repository.FriendRepository;
import com.adamszablewski.repository.FriendRequestRepository;
import com.adamszablewski.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class FriendService {
    private final FriendRepository friendRepository;
    private final RabbitMqProducer rabbitMqProducer;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendListRepository friendListRepository;
    private final UserServiceClient userServiceClient;
    private final Mapper mapper;
    public FriendListDto getFriendsForUser(long userId) {
        Friend friend = friendRepository.findByUserId(userId)
                .orElse(null);
        if (friend == null){
            return null;
        }
        return mapper.mapFriendlistToDto(friend.getFriendList());
    }

    public void removeFriend(long user1Id, long user2Id) {
        Friend user1 = friendRepository.findByUserId(user1Id)
                .orElseThrow(NoSuchUserException::new);

        Friend user2 = user1.getFriendList().getFriends().stream()
                .filter(friend -> friend.getUserId() == user2Id)
                .findFirst()
                .orElseThrow(NoSuchUserException::new);

        user1.getFriendList().getFriends().remove(user2);
        user2.getFriendList().getFriends().remove(user1);

        friendRepository.save(user1);
        friendListRepository.save(user1.getFriendList());
        friendRepository.save(user2);
        friendListRepository.save(user2.getFriendList());

    }

    private void addFriend(long user1Id, long user2Id) {
        Friend user1 = friendRepository.findByUserId(user1Id)
                .orElseGet(() -> createFriendForUser(user1Id));
        Friend user2 = friendRepository.findByUserId(user2Id)
                .orElseGet(() -> createFriendForUser(user2Id));

        user1.getFriendList().getFriends().add(user2);
        user2.getFriendList().getFriends().add(user1);

        friendRepository.save(user1);
        friendListRepository.save(user1.getFriendList());
        friendRepository.save(user2);
        friendListRepository.save(user2.getFriendList());
    }

    private Friend createFriendForUser(long user1Id) {

        FriendList friendList =  FriendList.builder()
                .friends(new HashSet<>())
                .build();
        friendListRepository.save(friendList);
        Friend friend = Friend.builder()
                .userId(user1Id)
                .friendList(friendList)
                .build();
        friendRepository.save(friend);
        return friend;
    }

    public void sendFriendRequest(long userId, long friendId) {
        String username = userServiceClient.getusernameFromId(userId);
        FriendRequest friendRequest = FriendRequest.builder()
                .status(FriendRequestStatus.RECEIVED)
                .dateTime(LocalDateTime.now())
                .senderId(userId)
                .senderUsername(username)
                .receiverId(friendId)
                .build();


        rabbitMqProducer.sendFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
    }

    private void acceptFriendRequest(FriendRequest friendRequest) {
        addFriend(friendRequest.getReceiverId(), friendRequest.getSenderId());
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);

        rabbitMqProducer.sendFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
    }

    private void declineFriendRequest(FriendRequest friendRequest) {
        addFriend(friendRequest.getReceiverId(), friendRequest.getSenderId());
        friendRequest.setStatus(FriendRequestStatus.DECLINED);

        rabbitMqProducer.sendFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
    }

    public void respondToFriendRequest(long friendRequestId, boolean status) {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId)
                .orElseThrow(NoFriendRequestException::new);

        if (status){
            acceptFriendRequest(friendRequest);
        }
        else{
            declineFriendRequest(friendRequest);
        }
    }
}
