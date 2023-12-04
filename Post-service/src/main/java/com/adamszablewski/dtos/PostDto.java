package com.adamszablewski.dtos;

import com.adamszablewski.classes.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private String text;
    private String multimediaId;
    private int likes;
    private String description;
}
