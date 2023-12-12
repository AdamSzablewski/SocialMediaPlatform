package com.adamszablewski.util;



import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendList;
import com.adamszablewski.dtos.FriendDto;
import com.adamszablewski.dtos.FriendListDto;
import com.adamszablewski.interfaces.Identifiable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public static  <T extends Identifiable> Set<Long> convertObjectListToIdSet(Collection<T> collection){
        return collection.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
    }
    public static <T extends Identifiable> Long convertObjectToId(T entity){
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
                .username(friend.getUsername())
                .build();
    }
    public Set<FriendDto> mapFriendToDto(Set<Friend> friends){
        Set<FriendDto> friendDtos = new HashSet<>();
        friends.forEach(friend -> friendDtos.add(mapFriendToDto(friend)));
        return friendDtos;
    }
}
