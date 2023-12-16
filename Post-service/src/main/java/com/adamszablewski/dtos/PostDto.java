package com.adamszablewski.dtos;

import com.adamszablewski.classes.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDto {
    private String text;
    private String multimediaId;
    private int likes;
    private Set<UpvoteDto> userLikeIds;
    private String description;
}
