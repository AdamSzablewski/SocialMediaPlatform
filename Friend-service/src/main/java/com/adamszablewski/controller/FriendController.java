package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendRequest;
import com.adamszablewski.dtos.FriendListDto;
import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.service.FriendService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;
    @GetMapping()
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public ResponseEntity<FriendListDto> getFriendsForUser(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(friendService.getFriendsForUser(userId));
    }
    @GetMapping("/requests")
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public ResponseEntity<List<FriendRequest>> getFriendRequestsForUser(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(friendService.getFriendRequestsForUser(userId));
    }
    @GetMapping(value = "/ids", produces = "application/json")
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public Flux<Long> getFriendIdsForUser(@RequestParam(name = "userId") long userId){
        System.out.println("friends |||   "+friendService.getFriendIdsForUser(userId));
        return friendService.getFriendIdsForUser(userId);
    }
    @GetMapping("/add")
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public ResponseEntity<Set<Friend>> sendFriendRequest(@RequestParam(name = "userId") long userId,
                                                         @RequestParam(name = "friendId") long friendId){
        friendService.sendFriendRequest(userId, friendId);
        return ResponseEntity.ok().build() ;
    }
    @GetMapping("/answere")
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public ResponseEntity<Set<Friend>> respondToFriendRequest(@RequestParam(name = "friendRequestId") long friendRequestId,
                                                              @RequestParam(name = "status")boolean status){
        friendService.respondToFriendRequest(friendRequestId, status);
        return ResponseEntity.ok().build() ;
    }
    @GetMapping("/remove")
    @SecureUserIdResource
    @CircuitBreaker(name = "friendServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "friendServiceRateLimiter")
    public ResponseEntity<String> removeFriend(@RequestParam(name = "userId") long userId,
                                               @RequestParam(name = "friendId") long friendId){
        friendService.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    public  ResponseEntity<?> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
