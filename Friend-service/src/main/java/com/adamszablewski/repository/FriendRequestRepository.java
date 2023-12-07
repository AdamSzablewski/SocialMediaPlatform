package com.adamszablewski.repository;

import com.adamszablewski.classes.Friend;
import com.adamszablewski.classes.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {


    Optional<FriendRequest> findByUserId(long userId);
    Optional<FriendRequest> findBySenderAndReceiver(long sender, long receiver);

}
