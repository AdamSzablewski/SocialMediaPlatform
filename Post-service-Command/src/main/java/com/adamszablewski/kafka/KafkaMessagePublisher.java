package com.adamszablewski.kafka;

import com.adamszablewski.events.CommentEvent;
import com.adamszablewski.events.PostEvent;
import com.adamszablewski.events.ProfileEvent;
import com.adamszablewski.events.UpvoteEvent;
import com.adamszablewski.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMessagePublisher {
    private KafkaTemplate<String, Long> template;
    private KafkaTemplate<String, String> templateStringString;
    private KafkaTemplate<String, Object> templateStringObject;

    public void sendUserDeletedMessage(Long userId){
        template.send(KafkaConfig.USER_DELETED, userId);
    }


    public void sendDeleteImageMessage(String multimediaId) {
        templateStringString.send(KafkaConfig.DELETE_IMAGE, multimediaId);
    }

    public void sendDeletedVideoMessage(String multimediaId) {
        templateStringString.send(KafkaConfig.DELETE_VIDEO, multimediaId);
    }

    public void sendPostEventMessage(PostEvent postEvent) {
        templateStringObject.send(KafkaConfig.POST_EVENT_TOPIC, postEvent);
    }
    public void sendCommentEventMessage(CommentEvent commentEvent) {
        templateStringObject.send(KafkaConfig.COMMENT_EVENT_TOPIC, commentEvent);
    }
    public void sendUpvoteEventMessage(UpvoteEvent upvoteEvent) {
        templateStringObject.send(KafkaConfig.UPVOTE_EVENT_TOPIC, upvoteEvent);
    }

    public void sendProfileEventMessage(ProfileEvent profileEvent) {
        templateStringObject.send(KafkaConfig.PROFILE_EVENT_TOPIC, profileEvent);
    }
}
