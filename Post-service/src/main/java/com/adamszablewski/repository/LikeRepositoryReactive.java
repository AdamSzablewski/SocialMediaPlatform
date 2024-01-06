//package com.adamszablewski.repository;
//
//import com.adamszablewski.classes.Feed;
//import com.adamszablewski.classes.Upvote;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//
//import java.util.Optional;
//
//public interface LikeRepositoryReactive extends ReactiveCrudRepository<Upvote, Long> {
//    Optional<Feed> findByUserId(long userId);
//}
