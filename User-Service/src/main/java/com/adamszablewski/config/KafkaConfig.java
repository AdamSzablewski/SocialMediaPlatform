package com.adamszablewski.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String USER_DELETED = "user-deleted";

    @Bean
    public NewTopic topic(){
        return TopicBuilder
                .name(USER_DELETED)
                .build();
    }
}
