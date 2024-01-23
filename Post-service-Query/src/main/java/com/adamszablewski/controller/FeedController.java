package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/posts/read/feed")
@AllArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping()
    @SecureUserIdResource
    public ResponseEntity<List<PostDto>> getFeedForUser(@RequestParam("userId") long userId,
                                                        HttpServletRequest servletRequest){
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeedForUser(userId));

    }
}
