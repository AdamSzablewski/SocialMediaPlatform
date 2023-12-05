package com.adamszablewski.repository;

import com.adamszablewski.classes.Feed;
import com.adamszablewski.classes.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Feed> findByUserId(long userId);
}
