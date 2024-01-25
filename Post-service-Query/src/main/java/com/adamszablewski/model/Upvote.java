package com.adamszablewski.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Upvote {
    @Id
    private long id;
    private long userId;
    private LocalDateTime dateTime;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Comment comment;
}
