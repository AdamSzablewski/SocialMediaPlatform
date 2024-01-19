package com.adamszablewski.kafka;

import com.adamszablewski.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adamszablewski.kafka.KafkaConfig.USER_DELETED;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private ImageService imageService;
    @KafkaListener(topics = USER_DELETED, groupId = "image-group")
    public void consumeUserDeleted(Long userId){
        imageService.deleteUserData(userId);

    }
}
