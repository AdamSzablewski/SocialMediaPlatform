package com.adamszablewski.service;

import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.UniqueIDServiceClient;
import com.adamszablewski.feign.VideoServiceClient;
import com.adamszablewski.kafka.KafkaMessagePublisher;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.PostType;
import com.adamszablewski.model.Profile;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import com.adamszablewski.utils.Dao;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageServiceClient imageServiceClient;
    private final ProfileRepository profileRepository;
    private final VideoServiceClient videoServiceClient;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final Dao dao;
    private final UniqueIDServiceClient uniqueIDServiceClient;

    public void deletePostById(long postId) {
        dao.deletePostById(postId);
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
    public Mono<String> postImagePost(long userId, MultipartFile image) {
        AtomicReference<String> multimediaID = new AtomicReference<>(uniqueIDServiceClient.getUniqueImageId());
        uploadImageAsync(image, userId, multimediaID.get())
                .doOnError(error -> multimediaID.set(""))
                .doOnSuccess(success -> createHiddenPost(PostType.IMAGE, userId, multimediaID.get()));
        System.out.println("done success");
        return Mono.just(multimediaID.get());
    }
    @Async
    private Mono<Void> uploadImageAsync(MultipartFile image, long userId, String multimediaID) {
        return Mono.fromFuture(CompletableFuture.runAsync(() -> {
            try {
                imageServiceClient.sendImageToImageService(image, userId, multimediaID);
            } catch (Exception e) {
                postRepository.deleteByMultimediaId(multimediaID);
            }
        }));
    }
    private void savePostDetails(long userId, String multimediaID) {
        Post newPost = Post.builder()
                .userId(userId)
                .likes(new HashSet<>())
                .multimediaId(multimediaID)
                .comments(new ArrayList<>())
                .visible(false)
                .build();

        Profile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> createProfile(userId));

        if (profile.getPosts() == null) {
            profile.setPosts(new ArrayList<>());
        }

        try {
            profile.getPosts().add(newPost);
            postRepository.save(newPost);
            profileRepository.save(profile);
        } catch (Exception e) {
            kafkaMessagePublisher.sendDeleteImageMessage(multimediaID);
            throw new RuntimeException("Error during post creation", e);
        }
    }
    private Profile createProfile(long userId){
        Profile newProfile = Profile.builder()
                .userId(userId)
                .posts(new ArrayList<>())
                .build();
        profileRepository.save(newProfile);
        return newProfile;
    }
    public void publishPost(String multimediaId, PostDto postDto) {
        Post post = postRepository.findByMultimediaId(multimediaId)
                .orElseThrow(NoSuchPostException::new);
        post.setDescription(postDto.getDescription());
        post.setVisible(true);
        postRepository.save(post);
    }
    @Transactional
    public Mono<String> uploadVideoForPost(long userId, MultipartFile video) {
        AtomicReference<String> multimediaID = new AtomicReference<>(uniqueIDServiceClient.getUniqueImageId());
       uploadVideoAsync(video, userId, multimediaID.get())
               .doOnError(error -> multimediaID.set(""))
               .doOnSuccess(success -> createHiddenPost(PostType.VIDEO, userId, multimediaID.get()));
       return Mono.just(multimediaID.get());
    }
    public void createHiddenPost(PostType type, long userId, String multimediaID){

        System.out.println("creting post");
        Post newPost = Post.builder()
                .userId(userId)
                .likes(new HashSet<>())
                .multimediaId(multimediaID)
                .comments(new ArrayList<>())
                .type(type)
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
        } catch (Exception e){
            kafkaMessagePublisher.sendDeletedVideoMessage(multimediaID);
            throw new RuntimeException();
        }

    }

    @Async
    public Mono<String> uploadVideoAsync(MultipartFile video, long userId, String multimediaID){
        return Mono.fromFuture(()->
                    CompletableFuture.runAsync(()->{
                        try {
                            videoServiceClient.sendImageToImageService(video, userId, multimediaID);
                        }catch (Exception e){
                            throw new RuntimeException("Failed to export video");
                        }
                    })

              ).thenReturn(multimediaID);
    }
}
