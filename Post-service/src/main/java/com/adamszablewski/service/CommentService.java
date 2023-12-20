package com.adamszablewski.service;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import com.adamszablewski.exceptions.NoSuchCommentException;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.utils.Dao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Dao dao;


    public List<Comment> getCommentsForPost(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchCommentException::new);
        return post.getComments();
    }
    public List<Comment> getCommentsForComment(long postId) {
        Comment comment = commentRepository.findById(postId)
                .orElseThrow(NoSuchCommentException::new);
        return comment.getAnswers();
    }
    @Transactional
    public void deleteCommentForPost(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchCommentException::new);
        Comment commentToRemove = post.getComments().stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .orElseThrow(NoSuchCommentException::new);
        post.getComments().remove(commentToRemove);
        dao.deleteComment(commentToRemove);
    }
    @Transactional
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);

        List<Comment> comments = comment.getAnswers();
        comments.add(comment);
        dao.deleteComment(comments);
    }
    @Transactional
    public void postCommentForPost(long postId, Comment commentData) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
        Comment comment = Comment.builder()
                .text(commentData.getText())
                .userId(commentData.getUserId())
                .build();
        post.getComments().add(comment);
        commentRepository.save(comment);
        postRepository.save(post);
    }
    @Transactional
    public void postCommentForComment(long commentId, Comment commentData) {
        Comment parent = commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
        Comment comment = Comment.builder()
                .text(commentData.getText())
                .userId(commentData.getUserId())
                .build();

        if (comment.getAnswers() == null){
             parent.setAnswers(new ArrayList<>());
        }
        parent.getAnswers().add(comment);
        commentRepository.save(comment);
        commentRepository.save(parent);
    }
}
