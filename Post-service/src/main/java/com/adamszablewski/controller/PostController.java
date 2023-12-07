package com.adamszablewski.controller;

import com.adamszablewski.classes.Post;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping()
    public ResponseEntity<Post> getPostById(@RequestParam(name = "postId") long postId){
       return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<String> deletePostById(@RequestParam(name = "postId") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<String> postPost(@RequestBody PostDto post,
                                           @RequestParam(name = "userId") long userId){
        postService.postPost(post, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam(name = "postId") long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
