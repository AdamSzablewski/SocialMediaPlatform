package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.service.LikeService;
import com.adamszablewski.utils.ExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/write/likes")
public class LikeController {

    private final ExceptionHandler exceptionHandler;
    private final LikeService likeService;


    @DeleteMapping("/posts")
    @SecureUserIdResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> unLikePost(@RequestParam("postId") long postId,
                                            @RequestParam("userId")long userId,
                                             HttpServletRequest servletRequest){
        likeService.unLikePost(postId, userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/comments")
    @SecureUserIdResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> unLikeComment(@RequestParam("commentId") long commentId,
                                              @RequestParam("userId")long userId,
                                                HttpServletRequest servletRequest){
        likeService.unLikeComment(commentId, userId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> fallBackMethod(Throwable throwable){
        return exceptionHandler.handleException(throwable);
    }
}
