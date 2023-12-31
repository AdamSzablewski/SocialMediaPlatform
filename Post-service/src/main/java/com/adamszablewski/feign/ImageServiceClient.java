package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "IMAGE-SERVICE")
public interface ImageServiceClient {

    @PostMapping("/images")
    String sendImageToImageServiceAndGetImageId(@RequestBody byte[] imageData, @RequestParam("userId") long userId);
    @GetMapping("/images/owner/{multimediaId}")
    long getOwnerForMultimediaId(@PathVariable long multimediaId);
}
