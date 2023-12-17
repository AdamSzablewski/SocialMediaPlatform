package com.adamszablewski.controller;

import com.adamszablewski.dto.RestResponseDTO;
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

    @PostMapping("/portfolio/facility/{facilityId}/user/{userId}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addPortfolioImage(@RequestParam("image")MultipartFile image,
                                                                     @PathVariable long facilityId,
                                                                     @PathVariable long userId,
                                                                     @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.addPortfolioImage(image, facilityId, userEmail, userId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/message/id/{imageId}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addMessageImage(@RequestBody byte[] imageData,
                                                                   @PathVariable String imageId,
                                                                   @RequestParam("recipients")Set<Long> recipients) throws IOException {
        imageService.addMessageImage(imageData, imageId, recipients);
        return ResponseEntity.ok(new RestResponseDTO<>());
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
    @GetMapping("/facilityID/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<?> getImageForFacility(@PathVariable long id) throws IOException {
        byte[] data = imageService.getImageForFacility(id);
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
    @PostMapping("/facilityID/{id}/user/{userId}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addFacilityImage(@RequestParam("image")MultipartFile file,
                                                                    @PathVariable long id,
                                                                    @RequestHeader("userEmail") String userEmail,
                                                                    @PathVariable long userId) throws IOException {
        imageService.addFacilityImage(id, file, userEmail, userId);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/userID/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> addUserImage(@RequestParam("image")MultipartFile file,
                                                                    @PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.addUserImage(id, file, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @DeleteMapping("/userID/{id}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> deleteUserImage(@PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.deleteUserImage(id, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    @DeleteMapping("/facilityID/{facilityID}")
    @CircuitBreaker(name = "imageServiceCircuitBreaker", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "imageServiceRateLimiter")
    public ResponseEntity<RestResponseDTO<String>> deleteFacilityImage(@PathVariable long facilityID,
                                                                    @RequestHeader("userEmail") String userEmail){
        imageService.deleteFacilityImage(facilityID, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }

    public  ResponseEntity<RestResponseDTO<?>> fallBackMethod(Throwable throwable){
        System.out.println("cb called");
        return CustomExceptionHandler.handleException(throwable);
    }
}
