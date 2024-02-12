package com.adamszablewski.controller;

import com.adamszablewski.exceptions.CustomExceptionHandler;
import com.adamszablewski.service.ImageService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> createPhotoResource(@RequestParam("image") MultipartFile image,
                                                      @RequestParam("userId") long userId)
    {
        String imageId = imageService.createPhotoResource(image, userId);
        return ResponseEntity.ok(imageId);
    }
    @PostMapping(value = "/upload/multimediaId/{multimediaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> createPhotoResource(@RequestParam("image") MultipartFile image,
                                                     @RequestParam("userId") long userId,
                                                     @PathVariable String multimediaId){
        String imageId = imageService.createPhotoResource(image, userId, multimediaId);
        return ResponseEntity.ok(imageId);
    }


    public  ResponseEntity<?> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
