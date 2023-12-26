package com.adamszablewski.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feed {

    private long userId;
//    @ManyToMany
//    @JoinTable(
//            name = "feed_posts",
//            joinColumns = @JoinColumn(name = "feed_id"),
//            inverseJoinColumns = @JoinColumn(name = "posts_id")
//    )
    private List<Post> posts;

}
