package com.adamszablewski.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMessagePublisher {
    private KafkaTemplate<String, Long> template;
    private KafkaTemplate<String, String> templateStringString;

    public void sendUserDeletedMessage(Long userId){
        template.send(KafkaConfig.USER_DELETED, userId);
    }


    public void sendDeleteImageMessage(String multimediaId) {
        templateStringString.send(KafkaConfig.DELETE_IMAGE, multimediaId);
    }

    public void sendDeletedVideoMessage(String multimediaId) {
        templateStringString.send(KafkaConfig.DELETE_VIDEO, multimediaId);
    }
}
