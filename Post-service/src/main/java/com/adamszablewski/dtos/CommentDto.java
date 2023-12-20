package com.adamszablewski.dtos;

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
public class CommentDto {

    private long id;
    private String text;
    private long userId;
    private Set<UpvoteDto> likes;
    private List<CommentDto> answers;
}
