package com.adamszablewski.repository;

import com.adamszablewski.classes.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByUserId(Long friend);

    Optional<Post> findByMultimediaId(String multimediaId);


}
