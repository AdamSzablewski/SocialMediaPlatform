package com.adamszablewski.repository;

import com.adamszablewski.classes.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByUserId(Long friend);

    Optional<Post> findByMultimediaId(String multimediaId);

    @Query("SELECT p FROM Post p JOIN p.comments c WHERE c.id = :commentId")
    Optional<Post> findByCommentId(@Param("commentId") long commentId);




}
