package com.adamszablewski.repository;

import com.adamszablewski.model.Comment;
import com.adamszablewski.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Feed> findByUserId(long userId);
}
