package adamszablewski;

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
        Post post = Post.builder()
                .id(1L)
                .comments(new ArrayList<>())
                .build();
        post.getComments().add(comment);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        commentService.deleteCommentForPost(postId, commentId);

        assertThat(post.getComments().contains(comment)).isFalse();
    }


}
