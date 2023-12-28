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

//    @PostMapping("/")
//    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
//        imageService.uploadImage(file);
//        return ResponseEntity.ok("File upploaded");
//    }

    @GetMapping()
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<byte[]> getImageByImageId(@RequestParam("multimediaId") String imageId)
    //@RequestHeader("userEmail") String userEmail) throws IOException
    {
        byte[] imageData = imageService.getImageByImageId(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
    @PostMapping()
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> addPostImage(@RequestBody byte[] imageArray,
                                               @RequestParam(name="userId") long userId) throws IOException
    //@RequestHeader("userEmail") String userEmail) throws IOException
                                                                {
        String imageId = imageService.addPostImage(imageArray, userId);
        return ResponseEntity.ok(imageId);
    }
    @DeleteMapping()
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> removePostImage(@RequestParam("postId") long postId)
    {
        imageService.removePostImage(postId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/owner/{multimediaId}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<Long> getOwnerForMultimediaId(@PathVariable() String multimediaId)
    {
        return ResponseEntity.ok(imageService.getOwnerForMultimediaId(multimediaId));
    }
    @PostMapping("/message")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> addMessageImage(@RequestBody byte[] imageData,
                                                  @RequestParam("recipients")Set<Long> recipients) throws IOException {
        String imageId = imageService.addMessageImage(imageData, recipients);
        return ResponseEntity.ok(imageId);
    }
    @GetMapping("/message/id/{imageId}/user/{userId}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<?> getMessageImage(@PathVariable String imageId, @PathVariable long userId) throws IOException {
        byte[] data = imageService.getImageForMessage(imageId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }

    @GetMapping("/userId/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<?> getImageForUser(@PathVariable long id) throws IOException {
        byte[] data = imageService.getImageForUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }

    @PostMapping("/userID/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> addUserImage(@RequestParam("image")MultipartFile file,
                                                                    @PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.addUserImage(id, file, userEmail);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/userID/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<String> deleteUserImage(@PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.deleteUserImage(id, userEmail);
        return ResponseEntity.ok().build();
    }


    public  ResponseEntity<?> fallBackMethod(Throwable throwable){
        System.out.println("cb called");
        return CustomExceptionHandler.handleException(throwable);
    }
}
