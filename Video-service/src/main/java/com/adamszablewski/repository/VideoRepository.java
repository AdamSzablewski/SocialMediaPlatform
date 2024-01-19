package com.adamszablewski.repository;

import com.adamszablewski.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    void deleteAllByUserId(Long userId);
}
