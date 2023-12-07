package com.adamszablewski.repository;

import com.adamszablewski.classes.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Set<Friend> findAllByUserId(long userId);

    Optional<Friend> findByUserId(long userId);
}
