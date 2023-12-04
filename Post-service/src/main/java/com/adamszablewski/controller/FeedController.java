package com.adamszablewski.controller;

import com.adamszablewski.classes.Post;
import com.adamszablewski.service.FeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/posts/feed")
@AllArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping()
    public ResponseEntity<List<Post>> getFeedForUser(@RequestParam("userId") long userId){
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeedForUser(userId));

    }
}
