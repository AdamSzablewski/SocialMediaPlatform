package com.adamszablewski.repository;

import com.adamszablewski.classes.Feed;
import com.adamszablewski.classes.Upvote;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Upvote, Long> {
    Optional<Feed> findByUserId(long userId);
}
