package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "FRIEND-SERVICE")
public interface FriendServiceClient {

    @GetMapping("/friends")
    List<Long> getFriendsForUser(@RequestParam("userId") long userId);
}
