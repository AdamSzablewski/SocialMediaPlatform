package com.adamszablewski.model;

import com.adamszablewski.interfaces.Likeable;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String text;
    private boolean visible;
    private String multimediaId;
    private PostType type;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Upvote> likes;
    private String description;
    private LocalDateTime dateTime;
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
                ", dateTime=" + dateTime +
                ", comments=" + comments +
                '}';
    }
}
