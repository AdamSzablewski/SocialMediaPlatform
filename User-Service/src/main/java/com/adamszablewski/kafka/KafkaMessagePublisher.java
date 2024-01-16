package com.adamszablewski.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMessagePublisher {
    private KafkaTemplate<String, Long> template;

    public void snedUserDeletedMessage(Long userId){
        template.send(KafkaConfig.USER_DELETED, userId);
    }


}
