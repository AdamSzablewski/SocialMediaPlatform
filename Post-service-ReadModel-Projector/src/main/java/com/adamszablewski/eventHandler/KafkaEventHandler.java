package com.adamszablewski.eventHandler;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.events.CommentEvent;
import com.adamszablewski.events.PostEvent;
import com.adamszablewski.events.ProfileEvent;
import com.adamszablewski.events.UpvoteEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adamszablewski.eventHandler.kafka.KafkaConfig.*;

@Component
@AllArgsConstructor
public class KafkaEventHandler {
    private final Dao dao;

    @KafkaListener(topics = POST_EVENT_TOPIC, groupId = GROUP)
    public void consumePostEventTopic(PostEvent event){
        dao.consumePostEvent(event);
    }
    @KafkaListener(topics = COMMENT_EVENT_TOPIC, groupId = GROUP)
    public void consumeCommentEventTopic(CommentEvent event){
        dao.consumeCommentEvent(event);
    }
    @KafkaListener(topics = UPVOTE_EVENT_TOPIC, groupId = GROUP)
    public void consumeUpvoteEventTopic(UpvoteEvent event){
        dao.consumeUpvoteEvent(event);
    }
    @KafkaListener(topics = PROFILE_EVENT_TOPIC, groupId = GROUP)
    public void consumeProfileEventTopic(ProfileEvent event){
        dao.consumeProfileEvent(event);
    }
}
