package com.adamszablewski;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Post;

import com.adamszablewski.repository.CommentRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.service.CommentService;
import com.adamszablewski.utils.Dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class CommentServiceTest {

    CommentService commentService;
    @Mock
    PostRepository postRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    Dao dao;

    @BeforeEach
    void setup(){
        commentService = new CommentService(postRepository, commentRepository, dao);
    }

    @Test
    void deleteCommentForPostTest_should_delete_comment(){
        long postId = 1L;
        long commentId = 1L;
        Comment comment = Comment.builder()
                .id(1L)
                .build();
        Comment comment2 = Comment.builder()
                .id(2L)
                .build();
        Post post = Post.builder()
                .id(1L)
                .comments(new ArrayList<>())
                .build();
        post.getComments().addAll(List.of(comment, comment2));

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        commentService.deleteCommentForPost(postId, commentId);

        assertThat(post.getComments().contains(comment)).isFalse();
        assertThat(post.getComments().contains(comment2)).isTrue();
        verify(dao).deleteComment(eq(comment));

    }

    @Test
    void postCommentForPostTest_should_post_comment() {
        long postId = 1L;
        Comment commentData = Comment.builder()
                .text("New Comment")
                .userId(1L)
                .build();
        Post post = Post.builder()
                .id(postId)
                .comments(new ArrayList<>())
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        commentService.postCommentForPost(postId, commentData);

        assertThat(post.getComments()).hasSize(1);
        Comment postedComment = post.getComments().get(0);
        assertThat(postedComment.getText()).isEqualTo("New Comment");
        assertThat(postedComment.getUserId()).isEqualTo(1L);
        verify(commentRepository).save(postedComment);
        verify(postRepository).save(post);
    }

    @Test
    void postCommentForCommentTest_should_post_comment_for_comment() {
        long parentCommentId = 1L;
        long userId = 2L;
        Comment parentComment = Comment.builder()
                .id(parentCommentId)
                .text("Parent Comment")
                .userId(1L).build();
        Comment commentData = Comment.builder()
                .text("Reply Comment")
                .userId(userId)
                .build();

        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));

        commentService.postCommentForComment(parentCommentId, commentData, userId);

        assertThat(parentComment.getAnswers()).hasSize(1);
        Comment postedComment = parentComment.getAnswers().get(0);
        assertThat(postedComment.getText()).isEqualTo("Reply Comment");
        assertThat(postedComment.getUserId()).isEqualTo(2L);
        verify(commentRepository).save(postedComment);
        verify(commentRepository).save(parentComment);
    }


}
