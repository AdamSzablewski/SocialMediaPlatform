package com.adamszablewski.service;

import com.adamszablewski.classes.Feed;
import com.adamszablewski.classes.Post;
import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.repository.FeedRepository;
import com.adamszablewski.repository.PostRepository;
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

    public List<Post> getFeedForUser(long userId){
        Feed feed = feedRepository.findByUserId(userId)
                .orElse(
                        Feed.builder()
                        .userId(userId)
                        .feed(new LinkedList<>())
                        .build()
                );

        updateFeed(feed);
        return feed.getFeed();

    }

    /**
     * Updates the users feed by adding new posts from users sorted from the newest to the oldest.
     * Removes post from feed if exceeds maximum of:
     * @param feed
     *
     */
    private void updateFeed(Feed feed){
        List<Long> friends = friendServiceClient.getFriendsForUser(feed.getUserId());
        PriorityQueue<Post> newPosts = new PriorityQueue<>(Comparator.comparing(Post::getDateTime));
        friends.forEach(friend -> {
            List<Post> posts = postRepository.getAllByUserId(friend);

            posts.forEach(post -> {
                if (!feed.getFeed().contains(post)){
                    newPosts.add(post);
                }
            });

        });
        while (!newPosts.isEmpty()){
            Post post = newPosts.poll();
            if(feed.getFeed().size() < MAX_FEED_SIZE){
                feed.getFeed().addFirst(post);
            }else {
                feed.getFeed().removeLast();
                feed.getFeed().addFirst(post);
            }
        }
        feedRepository.save(feed);
    }



}
