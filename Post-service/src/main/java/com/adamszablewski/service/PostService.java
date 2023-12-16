package com.adamszablewski.service;

import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Profile;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageServiceClient imageServiceClient;
    private final ProfileRepository profileRepository;
    public Post getPostById(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);
    }

    public void deletePostById(long postId) {
        postRepository.deleteById(postId);
    }

    public void postPost(PostDto post, long userId) {

        Post newPost = Post.builder()
                .text(post.getText())
                .userId(userId)
                .dateTime(LocalDateTime.now())
                .description(post.getDescription())
                .build();
        postRepository.save(newPost);
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }

    public void postImagePost(PostDto post, long userId, MultipartFile image) throws IOException {
        String imageId = imageServiceClient.sendImageToImageServiceAndGetImageId(image.getBytes(), userId);
        Post newPost = Post.builder()
                .userId(userId)
                .dateTime(LocalDateTime.now())
                .likes(new HashSet<>())
                .description(post.getDescription())
                .multimediaId(imageId)
                .comments(new ArrayList<>())
                .build();
        Profile profile = profileRepository.findByUserId(userId)
                        .orElseGet(()-> createProfile(userId));
        profile.getPosts().add(newPost);
        profileRepository.save(profile);
        postRepository.save(newPost);
    }
    private Profile createProfile(long userId){
        Profile newProfile = Profile.builder()
                .userId(userId)
                .build();
        profileRepository.save(newProfile);
        return newProfile;
    }
}
