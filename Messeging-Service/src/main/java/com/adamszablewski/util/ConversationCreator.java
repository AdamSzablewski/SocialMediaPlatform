package com.adamszablewski.util;


import com.adamszablewski.exceptions.NoSuchUserFoundException;
import com.adamszablewski.model.Conversation;
import com.adamszablewski.repository.ConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@AllArgsConstructor
@Component
public class ConversationCreator {
    private final ConversationRepository conversationRepository;

    public Conversation createConversation(long id, boolean isSystemConversation) {
        Conversation conversation = Conversation.builder()
                .ownerId(id)
                .isSystemConversation(isSystemConversation)
                .messages(new ArrayList<>())
                .build();
        conversationRepository.save(conversation);

        return conversation;
    }
    public Conversation createConversation(long ownerId, long recipientId) {
        Conversation conversation = Conversation.builder()
                .ownerId(ownerId)
                .recipientId(recipientId)
                .messages(new ArrayList<>())
                .isSystemConversation(false)
                .build();
        conversationRepository.save(conversation);

        return conversation;
    }
}
