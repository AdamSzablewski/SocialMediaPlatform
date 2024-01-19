package com.adamszablewski.repository;

import com.adamszablewski.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String name);

    Optional<ImageData> findByMultimediaId(String imageId);

    void deleteByMultimediaId(String multimediaId);

    void deleteAllByUserId(Long userId);
}
