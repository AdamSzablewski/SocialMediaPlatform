package com.adamszablewski.repository;

import com.adamszablewski.classes.Post;
import com.adamszablewski.classes.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(long userId);
}
