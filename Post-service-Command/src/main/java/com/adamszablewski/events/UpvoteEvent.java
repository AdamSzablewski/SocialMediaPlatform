package com.adamszablewski.events;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Upvote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UpvoteEvent {
    private EventType eventType;
    private Upvote comment;
}
