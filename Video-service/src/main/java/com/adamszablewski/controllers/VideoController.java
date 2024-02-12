package com.adamszablewski.controllers;

import com.adamszablewski.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private VideoService videoService;


    @PostMapping("/upload/s3")
    public ResponseEntity<String> uploadVideos3(@RequestParam("video") MultipartFile video,
                                              @RequestParam("multimediaId") String multimediaId,
                                                @RequestParam("userId") long userId) {
        videoService.createVideoEntity(video, multimediaId, userId);
        return ResponseEntity.ok().build();
    }

}
