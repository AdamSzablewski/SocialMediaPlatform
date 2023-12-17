package com.adamszablewski.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment implements Commentable, Likeable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private long userId;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Upvote> likes;
    @OneToMany
    private List<Comment> answers;



    @Override
    public List<Comment> getComments() {
        return answers;
    }
}
