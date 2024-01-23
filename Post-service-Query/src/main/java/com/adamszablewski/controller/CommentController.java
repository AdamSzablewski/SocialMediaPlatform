package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.model.Comment;
import com.adamszablewski.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/read/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping()
    public ResponseEntity<List<Comment>> getCommentsForPost(@RequestParam(name = "postId") long postId){
        return new ResponseEntity<>(commentService.getCommentsForPost(postId), HttpStatus.OK);
    }
    @GetMapping("/answers")
    public ResponseEntity<List<Comment>> getCommentsForComment(@RequestParam(name = "commentId") long commentId){
        return new ResponseEntity<>(commentService.getCommentsForComment(commentId), HttpStatus.OK);
    }

}