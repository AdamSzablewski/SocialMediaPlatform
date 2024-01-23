package com.adamszablewski.events;

import com.adamszablewski.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PostEvent {
    private EventType eventType;
    private Post post;
}
