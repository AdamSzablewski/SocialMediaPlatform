package com.adamszablewski.service;

import com.adamszablewski.dtos.PostDto;
import com.adamszablewski.feign.FriendServiceClient;
import com.adamszablewski.model.Post;
import com.adamszablewski.repository.PostRepository;
import com.adamszablewski.utils.CustomSortingUtil;
import com.adamszablewski.utils.FeedUtil;
import com.adamszablewski.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adamszablewski.utils.CustomSortingUtil.COMPARE_DAY_LIKE_COMMENT;

@Service
@AllArgsConstructor
public class FeedService {
    private final static int MAX_FEED_SIZE = 100;
    private final FeedUtil feedUtil;
    private final Mapper mapper;

    public List<PostDto> getFeedForUser(long userId) {

        return Stream.concat(
                feedUtil.getPostsForUser(userId).stream(),
                feedUtil.getFriendsForUser(userId).stream()
                        .map(feedUtil::getPostsForUser)
                        .flatMap(List::stream)
                )
                .sorted(COMPARE_DAY_LIKE_COMMENT)
                .map(mapper::mapPostToDto)
                .limit(MAX_FEED_SIZE)
                .collect(Collectors.toList());
    }


}
