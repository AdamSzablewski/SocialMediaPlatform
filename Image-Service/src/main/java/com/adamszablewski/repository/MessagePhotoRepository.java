package com.adamszablewski.repository;

import com.adamszablewski.model.MessagePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessagePhotoRepository extends JpaRepository<MessagePhoto, Long> {


    Optional<MessagePhoto> findByMultimediaId(String imageId);

    void deleteByMultimediaId(String multimediaId);
}

