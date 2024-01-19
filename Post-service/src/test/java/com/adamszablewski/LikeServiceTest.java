package com.adamszablewski;

import com.adamszablewski.interfaces.Likeable;
import com.adamszablewski.model.Comment;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @Mock
    private VideoServiceClient videoServiceClient;

    @Mock
    private Mapper mapper;

    @Mock
    private Dao dao;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        likeService = new LikeService(likeRepository, postRepository, commentRepository);
    }

}
