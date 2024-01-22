package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "UNIQUEID-SERVICE")
public interface UniqueIDServiceClient {

    @GetMapping("/uniqueID/image")
    String getUniqueImageId();
    @GetMapping("/uniqueID/video")
    String getUniqueVideoId();
}
