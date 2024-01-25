package com.adamszablewski.model;
import com.adamszablewski.interfaces.Likeable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment implements Commentable, Likeable {
    @Id
    private long id;
    private String text;
    private long userId;
    private LocalDateTime dateTime;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Upvote> likes = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> answers;



    @Override
    public List<Comment> getComments() {
        return answers;
    }
}
