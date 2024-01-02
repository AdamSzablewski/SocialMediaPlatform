package com.adamszablewski.controllers;

import com.adamszablewski.enteties.Video;
import com.adamszablewski.service.VideoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private static final int ChunkSize = 1024 * 1024; // 1 MB

    private VideoService videoService;


    @GetMapping(value = "/stream/{videoId}", produces = "video/mp4")
    public Mono<Resource> streamVideoByMultimediaId(@PathVariable Long videoId,
                                       @RequestHeader HttpHeaders headers) {
        return videoService.retrieveContent(videoId);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file,
                                              @RequestParam("name") String name,
                                              @RequestParam("contentType") String contentType) throws IOException {
        videoService.uploadVideo(file, name, contentType);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/upload/data")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") byte[] videoData,
                                              @RequestParam("contentType") String contentType,
                                              @RequestParam("userId") long userId) {
        String multimediaId = videoService.uploadVideo(videoData, contentType, userId);
        return ResponseEntity.ok(multimediaId);
    }
}
