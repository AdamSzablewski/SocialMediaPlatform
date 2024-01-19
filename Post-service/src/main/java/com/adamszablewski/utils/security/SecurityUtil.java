package com.adamszablewski.utils.security;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.exceptions.NoSuchCommentException;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.exceptions.NoSuchUpvoteException;
import com.adamszablewski.feign.ImageServiceClient;
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
    private final ImageServiceClient imageServiceClient;
    public boolean ownsPost(long postId, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
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

    public boolean ownsMultimedia(long multimediaId, String token) {
        long userId = securityServiceClient.extractUserIdFromToken(token);
        long ownerForResource = imageServiceClient.getOwnerForMultimediaId(multimediaId);
        return userId == ownerForResource;
    }

    public boolean isUser(long userId, String token) {
        long userIdFromToken = securityServiceClient.extractUserIdFromToken(token);
        System.out.println("userId from toke ||||  "+userIdFromToken);
        System.out.println("user id "+userId);
        return userId == userIdFromToken;
    }
}
