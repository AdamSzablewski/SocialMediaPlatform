package com.adamszablewski.utils;



import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Upvote;
import com.adamszablewski.dtos.CommentDto;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.dtos.UpvoteDto;
import com.adamszablewski.interfaces.Identifiable;
import com.adamszablewski.interfaces.UserResource;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public <T extends Identifiable> Set<Long> convertObjectListToIdSet(Collection<T> collection){
        return collection.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
    }
    public <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }
    public <T extends  UserResource> Long getUserId(T userResource) {
        return userResource.getUserId();
    }


    public List<PostDto> mapPostToDto(List<Post> posts) {
        return posts.stream()
                .map(this::mapPostToDto)
                .collect(Collectors.toList());
    }
    public PostDto mapPostToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .userLikeIds(mapUpvoteDto(post.getLikes()))
                .likes(countLikes(post))
                .comments(mapCommentToDto(post.getComments()))
                .text(post.getText())
                .multimediaId(post.getMultimediaId())
                .description(post.getDescription())
                .build();
    }
    public CommentDto mapCommentToDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .answers(mapCommentToDto(comment.getAnswers()))
                .userId(comment.getUserId())
                .likes(mapUpvoteDto(comment.getLikes()))
                .build();

    }
    public List<CommentDto> mapCommentToDto(List<Comment> comments){
        return comments.stream()
                .map(this::mapCommentToDto)
                .collect(Collectors.toList());
    }
    public UpvoteDto mapUpvoteDto(Upvote upvote){
        return UpvoteDto.builder()
                .userId(upvote.getUserId())
                .build();
    }
    public Set<UpvoteDto> mapUpvoteDto(Set<Upvote> upvotes){
        return upvotes.stream()
                .map(this::mapUpvoteDto)
                .collect(Collectors.toSet());
    }
    public int countLikes(Post post){
        return post.getLikes().size();
    }

}
