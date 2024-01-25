package com.adamszablewski.dtos;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpvoteDto {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    //@OneToOne
    private Post post;
    //@OneToOne
    private Comment comment;
}
