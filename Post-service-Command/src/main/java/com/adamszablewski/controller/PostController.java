package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.service.PostService;
import com.adamszablewski.utils.ExceptionHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/write")
public class PostController {

    private final PostService postService;
    private final ExceptionHandler exceptionHandler;

    @PostMapping()
    @SecureUserIdResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> postPost(HttpServletRequest servletRequest, @RequestBody PostDto post,
                                           @RequestParam(name = "userId") long userId){
        postService.postTextPost(post, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/image/upload")
    @SecureUserIdResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> uploadImageForPost(HttpServletRequest servletRequest,
                                                     @RequestParam(name = "userId") long userId,
                                                     @RequestParam MultipartFile image) {
        return ResponseEntity.ok(postService.uploadImageForPost(userId, image));
    }
    @PostMapping(value = "/video/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public String uploadVideoForPost(HttpServletRequest servletRequest, @RequestParam(name = "userId") long userId,
                                           @RequestParam("video") MultipartFile video) throws IOException {
        return postService.uploadVideoForPost(userId, video);
    }
    @PutMapping("/image")
    @SecureContentResource
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> publishImagePost(HttpServletRequest servletRequest,
                                                   @RequestParam(name = "multimediaId") String multimediaId,
                                                   @RequestBody PostDto postDto ) {
        postService.publishPost(multimediaId, postDto);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/video")
    @SecureContentResource(value = "multimediaId")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> publishVideoPost(HttpServletRequest servletRequest,
                                                   @RequestParam(name = "multimediaId") String multimediaId,
                                                   @RequestBody PostDto postDto ) {
        postService.publishPost(multimediaId, postDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete")
    @SecureContentResource(value = "postId")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> deletePost(HttpServletRequest servletRequest, @RequestParam(name = "postId") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping()
    @SecureContentResource(value = "postId")
    @CircuitBreaker(name = "postServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "postServiceRateLimiter")
    public ResponseEntity<String> deletePostById(HttpServletRequest servletRequest, @RequestParam(name = "postId") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> fallBackMethod(Throwable throwable){
        return exceptionHandler.handleException(throwable);
    }
}
