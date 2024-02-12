package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "VIDEO-SERVICE")
public interface VideoServiceClient {

    @PostMapping("/video/upload/data")
    String sendImageToImageServiceAndGetImageId(@RequestParam("file") byte[] videoData, @RequestParam("contentType") String contentType, long userId);
    @PostMapping(value = "/video/upload/file",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String sendImageToImageServiceAndGetImageId(@RequestPart("video") MultipartFile video,@RequestParam("userId") long userId);
    @PostMapping(value = "/video/upload/s3",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String sendVideoToVideoService(@RequestPart("video") MultipartFile video,@RequestParam("userId") long userId,@RequestParam("multimediaId") String multimediaId);
//    @PostMapping(value = "/video/upload/s3",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    String sendVideoToVideoService(@RequestPart("video") MultipartFile video,@RequestParam("userId") long userId);
    @GetMapping("/images/owner/{multimediaId}")
    long getOwnerForMultimediaId(@PathVariable long multimediaId);
    @PostMapping(value = "/video/upload/file",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void sendImageToImageService(@RequestPart("video") MultipartFile video,
                                 @RequestParam("userId") long userId,
                                 @RequestParam("multimediaId") String multimediaID);
}
