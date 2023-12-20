package com.adamszablewski.service;

import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Profile;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final RabbitMqProducer rabbitMqProducer;
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
                .visible(true)
                .description(post.getDescription())
                .build();
        postRepository.save(newPost);
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }
    @Transactional
    public String postImagePost(long userId, MultipartFile image) throws IOException {
        String multimediaId = imageServiceClient.sendImageToImageServiceAndGetImageId(image.getBytes(), userId);
        Post newPost = Post.builder()
                .userId(userId)
                .likes(new HashSet<>())
                .multimediaId(multimediaId)
                .comments(new ArrayList<>())
                .visible(false)
                .build();
        Profile profile = profileRepository.findByUserId(userId)
                .orElseGet(()-> createProfile(userId));
        if (profile.getPosts() == null){
            profile.setPosts(new ArrayList<>());
        }
        try{
            profile.getPosts().add(newPost);
            postRepository.save(newPost);
            profileRepository.save(profile);

        }
        catch (Exception e){
            rabbitMqProducer.deleteImageByMultimediaId(multimediaId);
            throw new RuntimeException();
        }
        return multimediaId;

    }
    private Profile createProfile(long userId){
        Profile newProfile = Profile.builder()
                .userId(userId)
                .posts(new ArrayList<>())
                .build();
        profileRepository.save(newProfile);
        return newProfile;
    }
    public void publishImagePost(String multimediaId, PostDto postDto) {
        Post post = postRepository.findByMultimediaId(multimediaId)
                .orElseThrow(NoSuchPostException::new);
        post.setDescription(postDto.getDescription());
        post.setVisible(true);
        postRepository.save(post);
    }
}
