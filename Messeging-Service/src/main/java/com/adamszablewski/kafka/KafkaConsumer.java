package com.adamszablewski.kafka;

import com.adamszablewski.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adamszablewski.kafka.KafkaConfig.USER_DELETED;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private final ConversationService conversationService;

    @KafkaListener(topics = USER_DELETED, groupId = "message-group")
    public void consumeUserDeleted(Long userId){
        conversationService.deleteConversationForUser(userId);
    }
}
