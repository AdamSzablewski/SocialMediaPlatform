package com.adamszablewski.utils;

import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.model.Post;
import com.adamszablewski.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FeedUtil {

    private final CustomSortingUtil sortingUtil;
    private final PostRepository postRepository;
    private final FriendServiceClient friendServiceClient;
    public List<Long> getFriendsForUser(long userId){
        return friendServiceClient.getFriendsForUser(userId);
    }
    public List<Post> getPostsForUser(long userId){
        return postRepository.getAllByUserId(userId).stream()
                .filter(Post::isVisible)
                .toList();
    }
}
