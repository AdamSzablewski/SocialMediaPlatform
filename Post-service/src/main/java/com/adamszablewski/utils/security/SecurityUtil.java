package com.adamszablewski.utils.security;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Upvote;
import com.adamszablewski.exceptions.NoSuchCommentException;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.exceptions.NoSuchUpvoteException;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.LikeRepository;
import com.adamszablewski.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SecurityUtil {

    private final SecurityServiceClient securityServiceClient;
    private final PostRepository postRepository;
    private final LikeRepository upvoteRepository;
    private final CommentRepository commentRepository;
    public boolean ownsPost(long postId, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
        System.out.println("owns post method called");
        long userId = securityServiceClient.extractUserIdFromToken(token);
        return post.getUserId() == userId;
    }

    public boolean ownsUpvote(long upvoteId, String token) {
        Upvote upvote = upvoteRepository.findById(upvoteId)
                .orElseThrow(NoSuchUpvoteException::new);
        long userId = securityServiceClient.extractUserIdFromToken(token);
        return upvote.getUserId() == userId;
    }

    public boolean ownsComment(long commentId, String token) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
        long userId = securityServiceClient.extractUserIdFromToken(token);
        return comment.getUserId() == userId;
    }
}
