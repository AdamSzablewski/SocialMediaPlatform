package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "VIDEO-SERVICE")
public interface VideoServiceClient {

    @PostMapping("/video/upload/data")
    String sendImageToImageServiceAndGetImageId(@RequestParam("file") byte[] videoData, @RequestParam("contentType") String contentType, long userId);
    @GetMapping("/images/owner/{multimediaId}")
    long getOwnerForMultimediaId(@PathVariable long multimediaId);
}
