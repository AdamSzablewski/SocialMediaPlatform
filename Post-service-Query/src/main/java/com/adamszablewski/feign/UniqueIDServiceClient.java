package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UNIQUEID-SERVICE")
public interface UniqueIDServiceClient {

    @GetMapping("/uniqueID/image")
    String getUniqueImageId();
    @GetMapping("/uniqueID/video")
    String getUniqueVideoId();
}
