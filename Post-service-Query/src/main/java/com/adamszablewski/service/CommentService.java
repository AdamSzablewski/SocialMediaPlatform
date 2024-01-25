//package com.adamszablewski.service;
//
//import com.adamszablewski.exceptions.NoSuchCommentException;
//import com.adamszablewski.model.Comment;
//import com.adamszablewski.model.Post;
//import com.adamszablewski.repository.CommentRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class CommentService {
//    //private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//
//    public List<Comment> getCommentsForPost(long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(NoSuchCommentException::new);
//        return post.getComments();
//    }
//    public List<Comment> getCommentsForComment(long postId) {
//        Comment comment = commentRepository.findById(postId)
//                .orElseThrow(NoSuchCommentException::new);
//        return comment.getAnswers();
//    }
//
//}