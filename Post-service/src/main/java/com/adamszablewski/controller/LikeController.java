package com.adamszablewski.controller;

import com.adamszablewski.service.LikeService;
import com.adamszablewski.utils.ExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/likes")
public class LikeController {

    private final ExceptionHandler exceptionHandler;
    private final LikeService likeService;

    @GetMapping("/posts")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> likePost(@RequestParam("postId") long postId,
                                           @RequestParam("userId")long userId){
        likeService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/comments")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> likeComment(@RequestParam("commentId") long commentId,
                                              @RequestParam("userId")long userId){
        likeService.likeComment(commentId, userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/posts")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> unLikePost(@RequestParam("postId") long postId,
                                           @RequestParam("userId")long userId){
        likeService.unLikePost(postId, userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/comments")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> unLikeComment(@RequestParam("commentId") long commentId,
                                              @RequestParam("userId")long userId){
        likeService.unLikeComment(commentId, userId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> fallBackMethod(Throwable throwable){
        return exceptionHandler.handleException(throwable);
    }
}
