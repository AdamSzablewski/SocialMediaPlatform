package com.adamszablewski.repository;

import com.adamszablewski.classes.Comment;
import com.adamszablewski.classes.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Feed> findByUserId(long userId);
}
