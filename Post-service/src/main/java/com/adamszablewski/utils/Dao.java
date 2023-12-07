package com.adamszablewski.utils;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Dao {
    private final CommentRepository commentRepository;
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
    public void deleteComment(List<Comment> comments){
        commentRepository.deleteAll(comments);
    }
}
