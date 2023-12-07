package com.adamszablewski.service;

import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendRequest;
import com.adamszablewski.classes.FriendRequestStatus;
import com.adamszablewski.exceptions.NoFriendRequestException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.FriendRepository;
import com.adamszablewski.repository.FriendRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class FriendService {
    private final FriendRepository friendRepository;
    private final RabbitMqProducer rabbitMqProducer;
    private final FriendRequestRepository friendRequestRepository;
    public Set<Friend> getFriendsForUser(long userId) {
        return friendRepository.findAllByUserId(userId);
    }
    @Transactional
    public void removeFriend(long userId, long friendId) {
        Friend user = friendRepository.findByUserId(userId)
                .orElseThrow(NoSuchUserException::new);

        Friend tempFriend = user.getFriends().stream()
                .filter(friend -> friend.getUserId() == friendId)
                .findFirst()
                .orElseThrow(NoSuchUserException::new);

        user.getFriends().remove(tempFriend);
        tempFriend.getFriends().remove(user);

        friendRepository.save(user);
        friendRepository.save(tempFriend);

    }
    @Transactional
    private void addFriend(long user1Id, long user2Id) {
        Friend user1 = friendRepository.findByUserId(user1Id)
                .orElseThrow(NoSuchUserException::new);
        Friend user2 = friendRepository.findByUserId(user2Id)
                .orElseThrow(NoSuchUserException::new);

        user1.getFriends().add(user2);
        user2.getFriends().add(user1);

        friendRepository.save(user1);
        friendRepository.save(user2);
    }

    public void sendFriendRequest(long userId, long friendId) {
        FriendRequest friendRequest = FriendRequest.builder()
                .status(FriendRequestStatus.RECEIVED)
                .dateTime(LocalDateTime.now())
                .senderId(userId)
                .receiverId(friendId)
                .build();

        rabbitMqProducer.sendFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
    }
    @Transactional
    private void acceptFriendRequest(FriendRequest friendRequest) {
        addFriend(friendRequest.getReceiverId(), friendRequest.getSenderId());
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);

        rabbitMqProducer.sendFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
    }
    @Transactional
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
