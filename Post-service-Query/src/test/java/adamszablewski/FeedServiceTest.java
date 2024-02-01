package adamszablewski;

import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Post;
import com.adamszablewski.model.Upvote;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.service.FeedService;
import com.adamszablewski.utils.CustomSortingUtil;
import com.adamszablewski.utils.FeedUtil;
import com.adamszablewski.utils.Mapper;
import io.jsonwebtoken.JwsHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;


public class FeedServiceTest {

    @Mock
    private FeedUtil feedUtil;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private FeedService feedService;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getFeedForUserTest_shouldReturnPostsSortedByCreationDate(){
        long userId = 1L;

        Post post1 = Post.builder()
                .id(1L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2023, 10, 1, 0, 0))
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2023, 12, 1, 0, 0))
                .build();
        Post post3 = Post.builder()
                .id(3L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2024, 2, 1, 0, 0))
                .build();
        PostDto postDto1 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2023, 10, 1, 0, 0))
                .build();
        PostDto postDto2 = PostDto.builder()
                .id(2L)
                .likes(0)
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2023, 12, 1, 0, 0))
                .build();
        PostDto postDto3 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .creationTime(LocalDateTime.of(2024, 2, 1, 0, 0))
                .build();
        when(feedUtil.getFriendsForUser(userId)).thenReturn(Arrays.asList(2L, 3L));
        when(feedUtil.getPostsForUser(userId)).thenReturn(Arrays.asList(post1));
        when(feedUtil.getPostsForUser(2L)).thenReturn(Arrays.asList(post2));
        when(feedUtil.getPostsForUser(3L)).thenReturn(Arrays.asList(post3));

        when(mapper.mapPostToDto(post1)).thenReturn(postDto1);
        when(mapper.mapPostToDto(post2)).thenReturn(postDto2);
        when(mapper.mapPostToDto(post3)).thenReturn(postDto3);
        List<PostDto> result = feedService.getFeedForUser(userId);
        assertThat(result.get(0)).isEqualTo(postDto3);
        assertThat(result.get(1)).isEqualTo(postDto2);
        assertThat(result.get(2)).isEqualTo(postDto1);
    }
    @Test
    void getFeedForUserTest_shouldReturnPostsSortedByLikeAmmount(){
        long userId = 1L;

        Post post1 = Post.builder()
                .id(1L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote()))
                .comments(new ArrayList<>())
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote()))
                .comments(new ArrayList<>())
                .build();
        Post post3 = Post.builder()
                .id(3L)
                .visible(true)
                .likes(Set.of(new Upvote()))
                .comments(new ArrayList<>())
                .build();
        PostDto postDto1 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        PostDto postDto2 = PostDto.builder()
                .id(2L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        PostDto postDto3 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        when(feedUtil.getFriendsForUser(userId)).thenReturn(Arrays.asList(2L, 3L));
        when(feedUtil.getPostsForUser(userId)).thenReturn(Arrays.asList(post1));
        when(feedUtil.getPostsForUser(2L)).thenReturn(Arrays.asList(post2));
        when(feedUtil.getPostsForUser(3L)).thenReturn(Arrays.asList(post3));

        when(mapper.mapPostToDto(post1)).thenReturn(postDto1);
        when(mapper.mapPostToDto(post2)).thenReturn(postDto2);
        when(mapper.mapPostToDto(post3)).thenReturn(postDto3);
        List<PostDto> result = feedService.getFeedForUser(userId);
        assertThat(result.get(0)).isEqualTo(postDto2);
        assertThat(result.get(1)).isEqualTo(postDto3);
        assertThat(result.get(2)).isEqualTo(postDto1);
    }
    @Test
    void getFeedForUserTest_shouldReturnPostsSortedByCommentAmmount(){
        long userId = 1L;

        Post post1 = Post.builder()
                .id(1L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(List.of(new Comment()))
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(List.of(new Comment(), new Comment(), new Comment()))
                .build();
        Post post3 = Post.builder()
                .id(3L)
                .visible(true)
                .likes(new HashSet<>())
                .comments(List.of(new Comment(), new Comment()))
                .build();
        PostDto postDto1 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        PostDto postDto2 = PostDto.builder()
                .id(2L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        PostDto postDto3 = PostDto.builder()
                .id(1L)
                .likes(0)
                .comments(new ArrayList<>())
                .build();
        when(feedUtil.getFriendsForUser(userId)).thenReturn(Arrays.asList(2L, 3L));
        when(feedUtil.getPostsForUser(userId)).thenReturn(Arrays.asList(post1));
        when(feedUtil.getPostsForUser(2L)).thenReturn(Arrays.asList(post2));
        when(feedUtil.getPostsForUser(3L)).thenReturn(Arrays.asList(post3));

        when(mapper.mapPostToDto(post1)).thenReturn(postDto1);
        when(mapper.mapPostToDto(post2)).thenReturn(postDto2);
        when(mapper.mapPostToDto(post3)).thenReturn(postDto3);
        List<PostDto> result = feedService.getFeedForUser(userId);
        assertThat(result.get(0)).isEqualTo(postDto2);
        assertThat(result.get(1)).isEqualTo(postDto3);
        assertThat(result.get(2)).isEqualTo(postDto1);
    }
    @Test
    void getFeedForUserTest_shouldReturnNoPosts(){
        long userId = 1L;

        when(feedUtil.getPostsForUser(userId)).thenReturn(new ArrayList<>());

        List<PostDto> result = feedService.getFeedForUser(userId);
        assertThat(result.size() == 0).isTrue();
    }
    @Test
    void getFeedForUserTest_shouldReturnPostsSortedByAll(){
        long userId = 1L;

        Post post1 = Post.builder()
                .id(1L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote(), new Upvote()))
                .comments(List.of(new Comment(), new Comment(), new Comment(), new Comment(), new Comment()))
                .creationTime(LocalDateTime.of(2023, 10, 2, 0, 0))
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote(), new Upvote()))
                .comments(List.of(new Comment(), new Comment(), new Comment(), new Comment()))
                .creationTime(LocalDateTime.of(2023, 10, 2, 0, 0))
                .build();
        Post post3 = Post.builder()
                .id(3L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote()))
                .comments(List.of(new Comment(), new Comment(), new Comment(), new Comment(), new Comment()))
                .creationTime(LocalDateTime.of(2023, 10, 2, 0, 0))
                .build();
        Post post4 = Post.builder()
                .id(4L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote()))
                .comments(List.of(new Comment(), new Comment(), new Comment(), new Comment()))
                .creationTime(LocalDateTime.of(2023, 10, 2, 0, 0))
                .build();
        Post post5 = Post.builder()
                .id(5L)
                .visible(true)
                .likes(Set.of(new Upvote(), new Upvote(), new Upvote(), new Upvote()))
                .comments(List.of(new Comment(), new Comment(), new Comment(), new Comment(), new Comment()))
                .creationTime(LocalDateTime.of(2023, 10, 1, 0, 0))
                .build();
        PostDto postDto1 = PostDto.builder()
                .id(1L)
                .build();
        PostDto postDto2 = PostDto.builder()
                .id(2L)
                .build();
        PostDto postDto3 = PostDto.builder()
                .id(3L)
                .build();
        PostDto postDto4 = PostDto.builder()
                .id(4L)
                .build();
        PostDto postDto5 = PostDto.builder()
                .id(5L)
                .build();
        when(feedUtil.getFriendsForUser(userId)).thenReturn(Arrays.asList(2L, 3L));
        when(feedUtil.getPostsForUser(userId)).thenReturn(Arrays.asList(post1, post5));
        when(feedUtil.getPostsForUser(2L)).thenReturn(Arrays.asList(post2));
        when(feedUtil.getPostsForUser(3L)).thenReturn(Arrays.asList(post3, post4));

        when(mapper.mapPostToDto(post1)).thenReturn(postDto1);
        when(mapper.mapPostToDto(post2)).thenReturn(postDto2);
        when(mapper.mapPostToDto(post3)).thenReturn(postDto3);
        when(mapper.mapPostToDto(post4)).thenReturn(postDto4);
        when(mapper.mapPostToDto(post5)).thenReturn(postDto5);
        List<PostDto> result = feedService.getFeedForUser(userId);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(2).getId()).isEqualTo(3L);
        assertThat(result.get(3).getId()).isEqualTo(4L);
        assertThat(result.get(4).getId()).isEqualTo(5L);
    }

}
