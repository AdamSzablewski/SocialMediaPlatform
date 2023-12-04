package com.adamszablewski.classes;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Friend {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    private Set<Friend> friends = new HashSet<>();
}
