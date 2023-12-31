package com.adamszablewski;

import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Profile;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.exceptions.NoSuchPostException;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.VideoServiceClient;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import com.adamszablewski.service.PostService;
import com.adamszablewski.utils.Dao;
import com.adamszablewski.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ImageServiceClient imageServiceClient;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @Mock
    private VideoServiceClient videoServiceClient;

    @Mock
    private Mapper mapper;

    @Mock
    private Dao dao;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, imageServiceClient, profileRepository,
                rabbitMqProducer, videoServiceClient, mapper, dao);
    }

    @Test
    void deletePostById_shouldDeletePost() {
        long postId = 1L;

        postService.deletePostById(postId);

        verify(dao).deletePost(postId);
    }

    @Test
    void postPost_shouldPostPost() {
        long userId = 1L;
        PostDto postDto = new PostDto();
        postDto.setText("New Post");

        postService.postPost(postDto, userId);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    void deletePost_shouldDeletePost() {
        long postId = 1L;

        postService.deletePost(postId);

        verify(postRepository).deleteById(postId);
    }

    @Test
    void postImagePost_shouldPostImagePost() throws IOException {
        long userId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(new byte[0]);
        when(imageServiceClient.sendImageToImageServiceAndGetImageId(any(), eq(userId))).thenReturn("imageId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));

        String multimediaId = postService.postImagePost(userId, image);

        verify(postRepository).save(any(Post.class));
        verify(profileRepository).save(any(Profile.class));
        assertThat(multimediaId).isEqualTo("imageId");
    }

    @Test
    void postImagePost_shouldRollbackOnException() throws IOException {
        long userId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(new byte[0]);
        when(imageServiceClient.sendImageToImageServiceAndGetImageId(any(), eq(userId))).thenReturn("imageId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));
        doThrow(new RuntimeException()).when(postRepository).save(any(Post.class));

        try {
            postService.postImagePost(userId, image);
        } catch (Exception ignored) {
        }

        verify(rabbitMqProducer).deleteImageByMultimediaId("imageId");
    }

    @Test
    void publishPost_shouldPublishPost() {
        String multimediaId = "imageId";
        PostDto postDto = new PostDto();
        postDto.setDescription("New Description");
        Post post = new Post();

        when(postRepository.findByMultimediaId(multimediaId)).thenReturn(Optional.of(post));

        postService.publishPost(multimediaId, postDto);

        assertThat(post.getDescription()).isEqualTo("New Description");
        assertThat(post.isVisible()).isTrue();
        verify(postRepository).save(post);
    }

    @Test
    void uploadVideoForPost_shouldUploadVideoForPost() throws IOException {
        long userId = 1L;
        MultipartFile video = mock(MultipartFile.class);
        when(videoServiceClient.sendImageToImageServiceAndGetImageId(any(), eq(userId))).thenReturn("videoId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));

        String multimediaId = postService.uploadVideoForPost(userId, video);

        verify(postRepository).save(any(Post.class));
        verify(profileRepository).save(any(Profile.class));
        assertThat(multimediaId).isEqualTo("videoId");
    }

    @Test
    void uploadVideoForPost_shouldRollbackOnException() throws IOException {
        long userId = 1L;
        MultipartFile video = mock(MultipartFile.class);
        when(videoServiceClient.sendImageToImageServiceAndGetImageId(any(), eq(userId))).thenReturn("videoId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));
        doThrow(new RuntimeException()).when(postRepository).save(any(Post.class));

        try {
            postService.uploadVideoForPost(userId, video);
        } catch (Exception ignored) {
        }

        verify(rabbitMqProducer).deleteImageByMultimediaId("videoId");
    }



}
