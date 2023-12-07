package com.adamszablewski.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Set<Long> recievers;

    private Message message;
}
