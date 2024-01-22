package com.adamszablewski.eventHandler.events;

import com.adamszablewski.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ProfileEvent {
    private EventType eventType;
    private Profile profile;
}
