package com.adamszablewski.repository;

import com.adamszablewski.model.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
    void deleteByUserId(long id);
    Optional<ProfilePhoto> findByUserId(long id);
}
