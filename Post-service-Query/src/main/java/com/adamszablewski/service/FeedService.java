package com.adamszablewski.service;

import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.model.Feed;
import com.adamszablewski.model.Post;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.utils.CustomSortingUtil;
import com.adamszablewski.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.*;

import static com.adamszablewski.utils.CustomSortingUtil.*;

@Service
@AllArgsConstructor
public class FeedService {

    private final static int MAX_FEED_SIZE = 100;

    private final PostRepository postRepository;

    private final FriendServiceClient friendServiceClient;
    private final CustomSortingUtil feedUtil;

    public List<PostDto> getFeedForUser(long userId){
        Feed feed = Feed.builder()
                        .userId(userId)
                        .posts(new LinkedList<>())
                        .build();

        updateFeed(feed);
        return Mapper.mapPostToDto(feed.getPosts());

    }



    /**
     * Updates the users feed by adding new posts from users sorted from the newest to the oldest.
     * Removes post from feed if exceeds maximum of: {@value MAX_FEED_SIZE}
     * @param feed
     *
     */
    @Transactional
    private void updateFeed(Feed feed){
        List<Long> friends = friendServiceClient.getFriendsForUser(feed.getUserId());
        PriorityQueue<Post> newPosts = new PriorityQueue<>(Comparator.comparing(Post::getCreationTime));
        addPostsForUserIdToList(feed, newPosts, feed.getUserId());

        friends.forEach(friendId -> addPostsForUserIdToList(feed, newPosts, friendId));

        while (!newPosts.isEmpty()){
            Post post = newPosts.poll();
            if(feed.getPosts().size() < MAX_FEED_SIZE){
                feed.getPosts().add(0, post);
            }else {
                feed.getPosts().remove(feed.getPosts().size()-1);
                feed.getPosts().add(0, post);
            }
        }

    }

    private void addPostsForUserIdToList(Feed feed, Collection<Post> newPosts, long userId){
        List<Post> posts = postRepository.getAllByUserId(userId);
        posts.forEach(System.out::println);
        posts.forEach(post -> {
            if (!feed.getPosts().contains(post)){
                feedUtil.sortComments(post);
                newPosts.add(post);
            }
        });

    }


}
