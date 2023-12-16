package com.adamszablewski.service;

import com.adamszablewski.classes.Feed;
import com.adamszablewski.classes.Post;
import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.repository.FeedRepository;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FeedService {

    private final static int MAX_FEED_SIZE = 100;

    private final PostRepository postRepository;
    private final FriendServiceClient friendServiceClient;
    private final FeedRepository feedRepository;
    private final Mapper mapper;

    public List<PostDto> getFeedForUser(long userId){
        Feed feed = feedRepository.findByUserId(userId)
                .orElse(
                        Feed.builder()
                        .userId(userId)
                        .posts(new LinkedList<>())
                        .build()
                );

        updateFeed(feed);
        feedRepository.save(feed);
        return mapper.mapPostToDto(feed.getPosts());

    }

    /**
     * Updates the users feed by adding new posts from users sorted from the newest to the oldest.
     * Removes post from feed if exceeds maximum of: {@value MAX_FEED_SIZE}
     * @param feed
     *
     */
    private void updateFeed(Feed feed){
        List<Long> friends = friendServiceClient.getFriendsForUser(feed.getUserId());
        System.out.println(friends);
        PriorityQueue<Post> newPosts = new PriorityQueue<>(Comparator.comparing(Post::getDateTime));
        List<Post> userPosts = postRepository.getAllByUserId(feed.getUserId());
        if (userPosts != null){
            userPosts.forEach(post -> {
                if (!feed.getPosts().contains(post)){
                    newPosts.add(post);
                }
            });
        }
        friends.forEach(friend -> {
            List<Post> posts = postRepository.getAllByUserId(friend);
            posts.forEach(post -> {
                if (!feed.getPosts().contains(post)){
                    newPosts.add(post);
                }
            });


        });
        while (!newPosts.isEmpty()){
            Post post = newPosts.poll();
            if(feed.getPosts().size() < MAX_FEED_SIZE){
                feed.getPosts().add(0, post);
            }else {
                feed.getPosts().remove(feed.getPosts().size()-1);
                feed.getPosts().add(0, post);
            }
        }
        feedRepository.save(feed);
    }



}
