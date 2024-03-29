package com.adamszablewski.model;

import com.adamszablewski.model.interfaces.Likeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post implements Commentable, Likeable {
    @Id
    private long id;
    private long userId;
    private PostType postType;
    private LocalDateTime creationTime;
    private String text;
    private boolean visible;
    private String multimediaId;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Upvote> likes;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", visible=" + visible +
                ", multimediaId='" + multimediaId + '\'' +
                ", likes=" + likes +
                ", description='" + description + '\'' +
                ", comments=" + comments +
                '}';
    }
}
