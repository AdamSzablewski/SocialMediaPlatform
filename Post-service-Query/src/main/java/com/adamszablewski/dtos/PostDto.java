package com.adamszablewski.dtos;

import com.adamszablewski.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDto {
    private long id;
    private long userId;
    private String text;
    private String multimediaId;
    private int likes;
    private List<CommentDto> comments;
    private Set<UpvoteDto> userLikeIds;
    private String description;
    private LocalDateTime creationTime;
    private PostType type;
}
