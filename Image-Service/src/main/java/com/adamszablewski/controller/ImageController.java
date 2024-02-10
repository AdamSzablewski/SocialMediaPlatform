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

    @GetMapping()
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
    public ResponseEntity<String> upploadImageToS3(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("multimediaId") String multimediaId)
    {
        String imageId = imageService.upploadImageToS3(file, multimediaId);
        return ResponseEntity.ok(imageId);
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> updateProfilePhoto(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("userId") long userId,
                                                     @RequestParam("multimediaId") String multimediaId)
    {
        String imageId = imageService.createPhotoResource(file, userId, multimediaId);
        return ResponseEntity.ok(imageId);
    }


    public  ResponseEntity<?> fallBackMethod(Throwable throwable){
        return CustomExceptionHandler.handleException(throwable);
    }
}
