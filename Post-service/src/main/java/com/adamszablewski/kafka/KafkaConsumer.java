package com.adamszablewski.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.adamszablewski.kafka.KafkaConfig.USER_DELETED;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = USER_DELETED, groupId = "post-group")
    public void userDeleted(long userId){
        System.out.println("user deleted "+userId);
    }
}
