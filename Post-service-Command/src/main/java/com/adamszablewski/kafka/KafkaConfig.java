package com.adamszablewski.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public static final String USER_DELETED = "user-deleted";
    public static final String DELETE_IMAGE = "image-deleted";
    public static final String DELETE_VIDEO = "delete-video";
    public static final String POST_EVENT_TOPIC = "post-event-topic";
    public static final String COMMENT_EVENT_TOPIC = "comment-event-topic";
    public static final String UPVOTE_EVENT_TOPIC = "upvote-event-topic";
    public static final String PROFILE_EVENT_TOPIC = "profile-event-topic";
}
