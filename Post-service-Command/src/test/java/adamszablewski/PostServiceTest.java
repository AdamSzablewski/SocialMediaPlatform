package adamszablewski;

import com.adamszablewski.feign.UniqueIDServiceClient;
import com.adamszablewski.kafka.KafkaMessagePublisher;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.PostType;
import com.adamszablewski.model.Profile;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.feign.ImageServiceClient;
import com.adamszablewski.feign.VideoServiceClient;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.repository.ProfileRepository;
import com.adamszablewski.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private VideoServiceClient videoServiceClient;
    @Mock
    private KafkaMessagePublisher kafkaMessagePublisher;
    @Mock
    private UniqueIDServiceClient uniqueIDServiceClient;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletePostById_shouldDeletePostAndSendEvent() {
        long postId = 1L;

        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePostById(postId);

        verify(postRepository).deleteById(postId);
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
    }
    @Test
    void postTextPost_shouldPostTextPost() {
        long userId = 1L;
        PostDto postDto = PostDto.builder()
                .text("New Post")
                .build();

        Post post = new Post();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.postTextPost(postDto, userId);

        verify(postRepository).save(any(Post.class));
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
    }

    @Test
    void fillTextPost_shouldFillTextPost() {
        PostDto postDto = PostDto.builder()
                .text("New Post")
                .build();
        Post post = Post.builder()
                .postType(PostType.TEXT)
                .build();

        postService.fillTextPost(post, postDto);

        assertThat(post.getText()).isEqualTo("New Post");
    }

    @Test
    void createPost_shouldCreatePost() {
        long userId = 1L;

        LocalDateTime currentTime = LocalDateTime.now();

        Post post = postService.createPost(PostType.IMAGE, userId);

        assertThat(post.getPostType()).isEqualTo(PostType.IMAGE);
        assertThat(post.getUserId()).isEqualTo(userId);
        assertThat(post.getComments()).isEmpty();
        assertThat(post.getLikes()).isEmpty();
        assertThat(post.isVisible()).isTrue();
        assertThat(post.getCreationTime()).isAfterOrEqualTo(currentTime);
    }

    @Test
    void uploadImageForPost_shouldUploadImageForPost() throws IOException {
        long userId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        when(uniqueIDServiceClient.getUniqueImageId()).thenReturn("uniqueImageId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));

        postService.uploadImageForPost(userId, image);

        verify(imageServiceClient).sendImageToImageService(eq(image), eq(userId), eq("uniqueImageId"));
        verify(postRepository).save(any(Post.class));
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
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
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
    }

    @Test
    void uploadVideoForPost_shouldUploadVideoForPost(){
        long userId = 1L;
        MultipartFile video = mock(MultipartFile.class);
        when(videoServiceClient.sendImageToImageServiceAndGetImageId(video, userId)).thenReturn("videoId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));

        postService.uploadVideoForPost(userId, video);

        verify(postRepository).save(any(Post.class));
        verify(profileRepository).save(any(Profile.class));
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
    }

    @Test
    void uploadVideoForPost_shouldRollbackOnException(){
        long userId = 1L;
        MultipartFile video = mock(MultipartFile.class);
        when(videoServiceClient.sendImageToImageServiceAndGetImageId(video, userId)).thenReturn("videoId");
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(new Profile()));
        doThrow(new RuntimeException()).when(postRepository).save(any(Post.class));

        assertThrows(RuntimeException.class, () -> postService.uploadVideoForPost(userId, video));

        verify(kafkaMessagePublisher).sendDeletedVideoMessage("videoId");
    }

    @Test
    void createHiddenPost_shouldCreateHiddenPost() {
        long userId = 1L;
        String multimediaId = "someMultimediaId";
        Post post = postService.createPost(PostType.VIDEO, userId);
        post.setVisible(false);
        Profile profile = new Profile();
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(profile));
        when(postRepository.save(post)).thenReturn(post);

        postService.createHiddenPost(PostType.VIDEO, userId, multimediaId);

        verify(postRepository).save(any(Post.class));
        verify(profileRepository).save(any(Profile.class));
        verify(kafkaMessagePublisher).sendPostEventMessage(any());
        verify(kafkaMessagePublisher).sendProfileEventMessage(any());
    }

    @Test
    void createHiddenPost_shouldRollbackOnException() {
        long userId = 1L;
        String multimediaId = "someMultimediaId";
        Post post = postService.createPost(PostType.VIDEO, userId);
        post.setVisible(false);
        Profile profile = new Profile();
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.of(profile));
        doThrow(new RuntimeException()).when(postRepository).save(any());

        assertThrows(RuntimeException.class, () -> postService.createHiddenPost(PostType.VIDEO, userId, multimediaId));

        verify(kafkaMessagePublisher).sendDeletedVideoMessage(multimediaId);
    }
}