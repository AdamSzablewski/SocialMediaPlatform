package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

//    @GetMapping
//    Map<String, Long> getIdsForMails(List<String> recipientMail);
    @GetMapping("/user/username")
    String getusernameFromId(@RequestParam long userId);
}
