package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "IMAGE-SERVICE")
public interface ImageServiceClient {

    @PostMapping("/images")
    String sendImageToImageServiceAndGetImageId(@RequestBody byte[] imageData, @RequestParam("userId") long userId);
    @GetMapping("/images/owner/{multimediaId}")
    long getOwnerForMultimediaId(@PathVariable long multimediaId);
    @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void sendImageToImageService(@RequestPart("image") MultipartFile image, @RequestParam("userId") long userId);
}
