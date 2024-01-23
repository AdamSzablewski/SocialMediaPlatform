package com.adamszablewski.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Profile {
    @Id
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    private long userId;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Upvote> upvotes = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

}
