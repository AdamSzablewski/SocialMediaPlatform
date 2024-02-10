package com.adamszablewski.repository;

import com.adamszablewski.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    void deleteByUserId(long id);
    Optional<Photo> findByUserId(long id);

    void deleteByMultimediaId(String multimediaId);

    void deleteAllByUserId(Long userId);
}
