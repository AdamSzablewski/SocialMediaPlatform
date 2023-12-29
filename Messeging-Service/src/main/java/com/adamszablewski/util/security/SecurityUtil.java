package com.adamszablewski.util.security;

import com.adamszablewski.exceptions.NoSuchConversationFoundException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SecurityUtil {

    private final SecurityServiceClient securityServiceClient;
    private final ConversationRepository conversationRepository;
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
        return userId == userIdFromToken;
    }

    public boolean ownsConversation(long conversationId, String token) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(NoSuchConversationFoundException::new);
        long userId = securityServiceClient.extractUserIdFromToken(token);
        return conversation.getOwnerId() == userId;
    }
}
