package com.adamszablewski.service;

import com.adamszablewski.classes.Post;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Post getPostById(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
    }

    public void deletePostById(long postId) {
    }

    public void postPost(PostDto post, long userId) {

        Post newPost = Post.builder()
                .text(post.getText())
                .description(post.getDescription())
                .multimediaId(post.getMultimediaId())
                .build();
        postRepository.save(newPost);
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }
}
