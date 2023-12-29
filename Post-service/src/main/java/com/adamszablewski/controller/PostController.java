package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.service.PostService;
import com.adamszablewski.utils.ExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ExceptionHandler exceptionHandler;

    @GetMapping()
    public ResponseEntity<PostDto> getPostById(HttpServletRequest servletRequest, @RequestParam(name = "postId") long postId){
       return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
    @DeleteMapping()
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> deletePostById(HttpServletRequest servletRequest, @RequestParam(name = "postId") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping()
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> postPost(HttpServletRequest servletRequest, @RequestBody PostDto post,
                                           @RequestParam(name = "userId") long userId){
        postService.postPost(post, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/image/upload")
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> uploadImageForPost(HttpServletRequest servletRequest, @RequestParam(name = "userId") long userId,
                                                @RequestParam MultipartFile image) throws IOException {
        String multimediaId = postService.postImagePost(userId, image);
        return ResponseEntity.ok(multimediaId);
    }
    @PostMapping("/image")
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> publishImagePost(HttpServletRequest servletRequest,
                                                   @RequestParam(name = "multimediaId") String multimediaId,
                                                   @RequestBody PostDto postDto ) {
        postService.publishImagePost(multimediaId, postDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete")
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> deletePost(HttpServletRequest servletRequest, @RequestParam(name = "postId") long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> fallBackMethod(Throwable throwable){
        return exceptionHandler.handleException(throwable);
    }
}
