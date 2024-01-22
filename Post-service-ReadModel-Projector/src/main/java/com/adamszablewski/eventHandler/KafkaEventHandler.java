package com.adamszablewski.eventHandler;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.eventHandler.events.CommentEvent;
import com.adamszablewski.eventHandler.events.PostEvent;
import com.adamszablewski.eventHandler.events.ProfileEvent;
import com.adamszablewski.eventHandler.events.UpvoteEvent;
import com.adamszablewski.eventHandler.kafka.KafkaConfig;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.adamszablewski.eventHandler.kafka.KafkaConfig.*;

@Component
@AllArgsConstructor
public class KafkaEventHandler {
    private final Dao dao;

    @KafkaListener(topics = POST_EVENT_TOPIC, groupId = "post-group-read")
    public void consumePostEventTopic(PostEvent event){
        dao.consumePostEvent(event);
    }
    @KafkaListener(topics = COMMENT_EVENT_TOPIC, groupId = "post-group-read")
    public void consumeCommentEventTopic(CommentEvent event){
        dao.consumeCommentEvent(event);
    }
    @KafkaListener(topics = UPVOTE_EVENT_TOPIC, groupId = "post-group-read")
    public void consumeUpvoteEventTopic(UpvoteEvent event){
        dao.consumeUpvoteEvent(event);
    }
    @KafkaListener(topics = PROFILE_EVENT_TOPIC, groupId = "post-group-read")
    public void consumeProfileEventTopic(ProfileEvent event){
        dao.consumeProfileEvent(event);
    }
}
