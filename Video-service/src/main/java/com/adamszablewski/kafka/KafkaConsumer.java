package com.adamszablewski.kafka;

import com.adamszablewski.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adamszablewski.kafka.KafkaConfig.USER_DELETED;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private VideoService videoService;
    @KafkaListener(topics = USER_DELETED, groupId = "video-group")
    public void consumeUserDeleted(Long userId){
        videoService.deleteUserData(userId);
    }
}
