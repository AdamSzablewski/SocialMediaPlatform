package com.adamszablewski.events;

import com.adamszablewski.model.Comment;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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
