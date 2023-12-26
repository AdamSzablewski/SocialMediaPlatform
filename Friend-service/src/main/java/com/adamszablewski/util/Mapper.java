package com.adamszablewski.util;



import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendList;
import com.adamszablewski.dtos.FriendDto;
import com.adamszablewski.dtos.FriendListDto;
import com.adamszablewski.interfaces.Identifiable;
import com.adamszablewski.interfaces.UserResource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public <T extends Identifiable> Set<Long> convertObjectListToIdSet(Collection<T> collection){
        return collection.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
    }
    public <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }

    public FriendListDto mapFriendlistToDto(FriendList friendList){
        return FriendListDto.builder()
                .id(friendList.getId())
                .friends(mapFriendToDto(friendList.getFriends()))
                .build();
    }
    public FriendDto mapFriendToDto(Friend friend){
        return FriendDto.builder()
                .friendListId(friend.getFriendList().getId())
                .userId(friend.getUserId())
                .build();
    }
    //todo add map useridendifiable mapper

    public Set<FriendDto> mapFriendToDto(List<Friend> friends){
        Set<FriendDto> friendDtos = new HashSet<>();
        friends.forEach(friend -> friendDtos.add(mapFriendToDto(friend)));
        return friendDtos;
    }

    public <T extends  UserResource> List<Long> convertObjectToUserId(List<T> userResource) {
        return userResource.stream()
                .map(UserResource::getUserId)
                .collect(Collectors.toList());
    }
    public <T extends  UserResource> Long convertObjectToUserId(T userResource) {
        return userResource.getUserId();
    }
}
