package com.adamszablewski.controller;

import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendRequest;
import com.adamszablewski.dtos.FriendListDto;
import com.adamszablewski.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;
    @GetMapping()
    public ResponseEntity<FriendListDto> getFriendsForUser(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(friendService.getFriendsForUser(userId));
    }
    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequest>> getFriendRequestsForUser(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(friendService.getFriendRequestsForUser(userId));
    }
    @GetMapping("/ids")
    public ResponseEntity<Set<Long>> getFriendIdsForUser(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(friendService.getFriendIdsForUser(userId));
    }
    @GetMapping("/add")
    public ResponseEntity<Set<Friend>> sendFriendRequest(@RequestParam(name = "userId") long userId,
                                                 @RequestParam(name = "friendId") long friendId){
        friendService.sendFriendRequest(userId, friendId);
        return ResponseEntity.ok().build() ;
    }
    @GetMapping("/answere")
    public ResponseEntity<Set<Friend>> respondToFriendRequest(@RequestParam(name = "friendRequestId") long friendRequestId,
                                                           @RequestParam(name = "status")boolean status){
        friendService.respondToFriendRequest(friendRequestId, status);
        return ResponseEntity.ok().build() ;
    }
    @GetMapping("/remove")
    public ResponseEntity<Set<Friend>> removeFriend(@RequestParam(name = "userId") long userId,
                                                 @RequestParam(name = "friendId") long friendId){
        friendService.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }
}
