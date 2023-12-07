package com.adamszablewski.feign;


import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "IMAGE-SERVICE")
public interface ImageServiceClient {

    @PostMapping("/images/message/id/{imageId}")
    void sendImageToImageService(@RequestBody byte[] image,
                                 @PathVariable String imageId,
                                 @RequestParam("recipients") Set<Long> recipients);


}
