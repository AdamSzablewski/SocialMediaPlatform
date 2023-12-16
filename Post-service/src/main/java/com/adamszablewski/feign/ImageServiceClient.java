package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "IMAGE-SERVICE")
public interface ImageServiceClient {

    @PostMapping("/images/message/id/{imageId}")
    String sendImageToImageServiceAndGetImageId(@RequestBody byte[] image, @RequestParam("userId") long userId);
}
