package com.adamszablewski.service;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchCommentException;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


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
}
