package com.adamszablewski.repository;

import com.adamszablewski.model.Feed;
import com.adamszablewski.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Upvote, Long> {
    Optional<Feed> findByUserId(long userId);
}
