package com.adamszablewski;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.utils.CustomSortingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class CustomSortingUtilsTest {

    CustomSortingUtil customSortingUtil;


    @BeforeEach
    void setup(){
        customSortingUtil = new CustomSortingUtil();
    }

    @Test
    void sortComments_test_should_sort_desc_based_on_comment_amount_then_desc_like_amount(){
        Upvote upvote1 = Upvote.builder()
                .id(1L)
                .build();
        Upvote upvote2 = Upvote.builder()
                .id(2L)
                .build();
        Upvote upvote3 = Upvote.builder()
                .id(3L)
                .build();
        Upvote upvote4 = Upvote.builder()
                .id(4L)
                .build();
        Comment answereComment = Comment.builder()
                .answers(new ArrayList<>())
                .likes(new HashSet<>())
                .id(1L)
                .build();
        Comment commentWithOneAns = Comment.builder()
                .likes(new HashSet<>())
                .id(2L)
                .answers(List.of(answereComment))
                .build();
        Comment commentWithThreeUpvotes = Comment.builder()
                .answers(new ArrayList<>())
                .likes(Set.of(upvote1, upvote2, upvote3))
                .id(3L)
                .build();
        Comment commentWithOneUpvote = Comment.builder()
                .answers(new ArrayList<>())
                .likes(Set.of(upvote4))
                .id(4L)
                .build();
        Post post = Post.builder()
                .id(1L)
                .comments(List.of(commentWithThreeUpvotes, commentWithOneAns, commentWithOneUpvote))
                .build();

        customSortingUtil.sortComments(post);
        assertThat(post.getComments().get(0).getId()).isEqualTo(2L);
        assertThat(post.getComments().get(1).getId()).isEqualTo(3L);
        assertThat(post.getComments().get(2).getId()).isEqualTo(4L);


    }
    @Test
    void sortComments_test_should_sort_desc_based_on_creatinon_dateTime(){


        Comment comment1 = Comment.builder()
                .answers(new ArrayList<>())
                .likes(new HashSet<>())
                .dateTime(LocalDateTime.of(LocalDate.of(2023, 10, 12), LocalTime.of(22, 0)))
                .id(1L)
                .build();
        Comment comment2 = Comment.builder()
                .likes(new HashSet<>())
                .dateTime(LocalDateTime.of(LocalDate.of(2023, 12, 12), LocalTime.of(22, 0)))
                .id(2L)
                .answers(new ArrayList<>())
                .build();
        Comment comment3 = Comment.builder()
                .answers(new ArrayList<>())
                .likes(new HashSet<>())
                .dateTime(LocalDateTime.of(LocalDate.of(2023, 2, 12), LocalTime.of(22, 0)))
                .id(3L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .comments(List.of(comment1, comment2, comment3))
                .build();

        customSortingUtil.sortComments(post);
        assertThat(post.getComments().get(0).getId()).isEqualTo(2L);
        assertThat(post.getComments().get(1).getId()).isEqualTo(1L);
        assertThat(post.getComments().get(2).getId()).isEqualTo(3L);


    }


}
