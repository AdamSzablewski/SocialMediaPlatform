package com.adamszablewski;

import com.adamszablewski.interfaces.Likeable;
import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.feign.VideoServiceClient;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.LikeRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.service.LikeService;
import com.adamszablewski.utils.Dao;
import com.adamszablewski.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(likeRepository, postRepository, commentRepository);
    }
    @Test
    void checkIfUserAlreadyLikedTesT_shouldReturnTrue_forComment(){

        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(userId)
                .build();
        Upvote upvote2 = Upvote.builder()
                .userId(2L)
                .build();
        Comment comment = Comment.builder()
                .likes(Set.of(upvote1, upvote2))
                .build();
        boolean result = likeService.checkIfUserAlreadyLiked(comment, userId);
        assertThat(result).isTrue();
    }
    @Test
    void checkIfUserAlreadyLikedTesT_shouldReturnFalse_forComment(){

        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(2L)
                .build();
        Comment comment = Comment.builder()
                .likes(Set.of(upvote1))
                .build();
        boolean result = likeService.checkIfUserAlreadyLiked(comment, userId);
        assertThat(result).isFalse();
    }
    @Test
    void checkIfUserAlreadyLikedTesT_shouldReturnTrue_forPost(){

        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(userId)
                .build();
        Upvote upvote2 = Upvote.builder()
                .userId(2L)
                .build();
        Post post = Post.builder()
                .likes(Set.of(upvote1, upvote2))
                .build();
        boolean result = likeService.checkIfUserAlreadyLiked(post, userId);
        assertThat(result).isTrue();
    }
    @Test
    void checkIfUserAlreadyLikedTesT_shouldReturnFalse_forPost(){

        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(2L)
                .build();
        Post post = Post.builder()
                .likes(Set.of(upvote1))
                .build();
        boolean result = likeService.checkIfUserAlreadyLiked(post, userId);
        assertThat(result).isFalse();
    }
    @Test
    void removeLikeTest_shouldRemoveFromPost(){
        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(userId)
                .build();
        Upvote upvote2 = Upvote.builder()
                .userId(2L)
                .build();
        Post post = Post.builder()
                .likes(new HashSet<>())
                .build();
        post.getLikes().add(upvote1);
        post.getLikes().add(upvote2);
        likeService.removeLike(post, userId);
        assertThat(post.getLikes().size()).isEqualTo(1);
        assertThat(post.getLikes().contains(upvote1)).isFalse();
    }
    @Test
    void removeLikeTest_shouldNotRemoveFromPost(){
        long userId = 1L;
        Post post = Post.builder()
                .likes(new HashSet<>())
                .build();
        likeService.removeLike(post, userId);
        assertThat(post.getLikes().size()).isEqualTo(0);
    }
    @Test
    void removeLikeTest_shouldRemoveFromComment(){
        long userId = 1L;
        Upvote upvote1 = Upvote.builder()
                .userId(userId)
                .build();
        Upvote upvote2 = Upvote.builder()
                .userId(2L)
                .build();
        Comment comment = Comment.builder()
                .likes(new HashSet<>())
                .build();
        comment.getLikes().add(upvote1);
        comment.getLikes().add(upvote2);
        likeService.removeLike(comment, userId);
        assertThat(comment.getLikes().size()).isEqualTo(1);
        assertThat(comment.getLikes().contains(upvote1)).isFalse();
    }
    @Test
    void removeLikeTest_shouldNotRemoveFromComment(){
        long userId = 1L;
        Comment comment = Comment.builder()
                .likes(new HashSet<>())
                .build();
        likeService.removeLike(comment, userId);
        assertThat(comment.getLikes().size()).isEqualTo(0);
    }
    @Test
    void likePostTest_shouldLikePost(){
        long userId = 1L;

        Comment comment = Comment.builder()
                .id(1L)
                .likes(new HashSet<>())
                .build();
        Upvote upvote1 = Upvote.builder()
                .comment(comment)
                .userId(userId)
                .build();
        Comment result = Comment.builder()
                .id(1L)
                .likes(Set.of(upvote1))
                .build();

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        likeService.likeComment(comment.getId(), userId);

        assertThat(comment.getLikes().size()).isEqualTo(1);

    }
}
