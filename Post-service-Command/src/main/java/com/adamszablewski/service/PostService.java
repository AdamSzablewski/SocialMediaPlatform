package com.adamszablewski.service;

import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.events.EventType;
import com.adamszablewski.events.PostEvent;
import com.adamszablewski.events.ProfileEvent;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.exceptions.WrongTypeException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.UniqueIDServiceClient;
import com.adamszablewski.feign.VideoServiceClient;
import com.adamszablewski.kafka.KafkaMessagePublisher;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.PostType;
import com.adamszablewski.model.Profile;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
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

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageServiceClient imageServiceClient;
    private final ProfileRepository profileRepository;
    private final VideoServiceClient videoServiceClient;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final UniqueIDServiceClient uniqueIDServiceClient;

    public void deletePostById(long postId) {
        postRepository.deleteById(postId);
        Post post = Post.builder()
                .id(postId)
                .build();
        kafkaMessagePublisher.sendPostEventMessage(new PostEvent(EventType.DELETE, post));
    }

    public void postTextPost(PostDto postDto, long userId) {
        Post post = createPost(PostType.TEXT, userId);
        fillTextPost(post, postDto);
        postRepository.save(post);
        kafkaMessagePublisher.sendPostEventMessage(new PostEvent(EventType.CREATE, post));
    }
    public Post fillTextPost(Post post, PostDto postDto){
        if (post.getPostType() != PostType.TEXT){
            throw new WrongTypeException();
        }
        post.setText(postDto.getText());
        return post;
    }
    public Post createPost(PostType postType,  long userId){
        return Post.builder()
                .postType(postType)
                .userId(userId)
                .comments(new ArrayList<>())
                .likes(new HashSet<>())
                .visible(true)
                .creationTime(LocalDateTime.now())
                .build();
    }
    @Transactional
    public String uploadImageForPost(long userId, MultipartFile image) {
        String multimediaId = uniqueIDServiceClient.getUniqueImageId();
        CompletableFuture.runAsync(()-> imageServiceClient.sendImageToImageService(image, userId, multimediaId));
        createHiddenPost(PostType.IMAGE, userId, multimediaId);
        return multimediaId;
    }
    private Profile createProfile(long userId){
        Profile newProfile = Profile.builder()
                .userId(userId)
                .posts(new ArrayList<>())
                .build();
        profileRepository.save(newProfile);
        kafkaMessagePublisher.sendProfileEventMessage(new ProfileEvent(EventType.CREATE, newProfile));
        return newProfile;
    }
    public void publishPost(String multimediaId, PostDto postDto) {
        Post post = postRepository.findByMultimediaId(multimediaId)
                .orElseThrow(NoSuchPostException::new);
        post.setDescription(postDto.getDescription());
        post.setCreationTime(LocalDateTime.now());
        post.setVisible(true);
        postRepository.save(post);
        kafkaMessagePublisher.sendPostEventMessage(new PostEvent(EventType.UPDATE, post));
    }
    public String uploadVideoForPost(long userId, MultipartFile video) {
        String multimediaId = videoServiceClient.sendImageToImageServiceAndGetImageId(video, userId);
        createHiddenPost(PostType.VIDEO,userId, multimediaId);
        return multimediaId;

    }
    @Async
    @Transactional
    public void createHiddenPost(PostType type, long userId, String multimediaID){

            Post post = createPost(type, userId);
            post.setVisible(false);
            Profile profile = profileRepository.findByUserId(userId)
                    .orElseGet(()-> createProfile(userId));
            try{
                if (profile.getPosts() == null){
                    profile.setPosts(new ArrayList<>());
                }
                profile.getPosts().add(post);
                System.out.println("|||||||   "+post);
                postRepository.save(post);
                profileRepository.save(profile);
                kafkaMessagePublisher.sendPostEventMessage(new PostEvent(EventType.CREATE, post));
                kafkaMessagePublisher.sendProfileEventMessage(new ProfileEvent(EventType.UPDATE, profile));
            } catch (Exception e){
                if (type == PostType.VIDEO){
                    kafkaMessagePublisher.sendDeletedVideoMessage(multimediaID);
                }else {
                    kafkaMessagePublisher.sendDeleteImageMessage(multimediaID);
                }

                throw new RuntimeException();
            }

    }

}
