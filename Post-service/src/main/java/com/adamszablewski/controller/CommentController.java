package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.classes.Comment;
import com.adamszablewski.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts/comments")
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
    @PostMapping()
    @SecureUserIdResource
    public ResponseEntity<String> postCommentForPost(@RequestParam(name = "postId") long postId,
                                                      @RequestParam(name = "userId") long userId,
                                                      @RequestBody Comment comment){
        commentService.postCommentForPost(postId, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/comment")
    @SecureUserIdResource
    public ResponseEntity<String> postCommentsForComment(@RequestParam(name = "commentId") long commentId,
                                                         @RequestParam(name = "userId") long userId,
                                                         @RequestBody Comment comment){
        commentService.postCommentForComment(commentId, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    @SecureContentResource
    public ResponseEntity<String> deleteCommentsForPost(@RequestParam(name = "postId") long postId,
                                                      @RequestParam(name = "commentId") long commentId){
        commentService.deleteCommentForPost(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @SecureContentResource
    public ResponseEntity<String> deleteComment(@RequestParam(name = "commentId") long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
