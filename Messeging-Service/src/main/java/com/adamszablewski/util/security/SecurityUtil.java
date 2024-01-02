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
