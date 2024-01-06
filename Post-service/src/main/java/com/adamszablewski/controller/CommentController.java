package com.adamszablewski.controller;

import com.adamszablewski.annotations.SecureContentResource;
import com.adamszablewski.annotations.SecureUserIdResource;
import com.adamszablewski.classes.Comment;
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
                                                     @RequestBody Comment comment,
                                                     HttpServletRequest servletRequest){
        commentService.postCommentForPost(postId, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/comment")
    @SecureUserIdResource
    public ResponseEntity<String> postCommentForComment(@RequestParam(name = "commentId") long commentId,
                                                         @RequestParam(name = "userId") long userId,
                                                         @RequestBody Comment comment,
                                                        HttpServletRequest servletRequest){
        commentService.postCommentForComment(commentId, comment, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    @SecureContentResource(value = "commentId")
    public ResponseEntity<String> deleteCommentForPost(@RequestParam(name = "postId") long postId,
                                                        @RequestParam(name = "commentId") long commentId,
                                                       HttpServletRequest servletRequest){
        commentService.deleteCommentForPost(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @SecureContentResource(value = "commentId")
    public ResponseEntity<String> deleteComment(@RequestParam(name = "parentId") long parentId,
                                                @RequestParam(name = "commentId") long commentId,
                                                HttpServletRequest servletRequest){
        commentService.deleteCommentForComment(parentId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}