package com.adamszablewski.classes;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
public class FriendList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany
    @JoinTable(
            name = "friend_list_friends",
            joinColumns = @JoinColumn(name = "friend_list_id"),
            inverseJoinColumns = @JoinColumn(name = "friends_id")
    )
    private List<Friend> friends;



}
