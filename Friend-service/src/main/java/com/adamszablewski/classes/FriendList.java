package com.adamszablewski.classes;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
public class FriendList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private Set<Friend> friends;
    @Override
    public String toString() {
        Set<Long> friendIds = new HashSet<>();
        friends.forEach(friend -> friendIds.add(friend.getUserId()));

        return "FriendList{" +
                "friends=" + friendIds +
                '}';
    }
}
