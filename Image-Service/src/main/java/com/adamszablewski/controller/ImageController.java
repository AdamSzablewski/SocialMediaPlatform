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

import java.io.IOException;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;


    @GetMapping("/s3")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<byte[]> getImageByImageIdFromS3(@RequestParam("multimediaId") String imageId)
    {
        byte[] imageData = imageService.getImageByImageIdS3(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @PostMapping(value = "/s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> upploadImageToS3(@RequestParam("file") MultipartFile file)
    {
        String imageId = imageService.upploadImageToS3(file);
        return ResponseEntity.ok(imageId);
    }

    public  ResponseEntity<?> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
