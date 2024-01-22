package com.adamszablewski.eventHandler.events;

import com.adamszablewski.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CommentEvent {
    private EventType eventType;
    private Comment comment;
}
