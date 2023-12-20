package com.adamszablewski.classes;

import com.adamszablewski.interfaces.Likeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post implements Commentable, Likeable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String text;
    private boolean visible;
    private String multimediaId;
    @ManyToMany(mappedBy = "posts")
    List<Feed> feeds;
    @OneToMany
    private Set<Upvote> likes;
    private String description;
    private LocalDateTime dateTime;
    @OneToMany
    private List<Comment> comments;
}
