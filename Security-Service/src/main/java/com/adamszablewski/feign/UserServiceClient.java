package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/users/passwords/getHashed/user/{userEmail}")
    RestResponseDTO<String> getHashedPassword(@PathVariable String userEmail);
    @GetMapping("/users/{userId}/{userEmail}")
    RestResponseDTO<Boolean> isUser(@PathVariable long userId,@PathVariable String userEmail);
    @GetMapping("user/convert")
    long getUserIdFromUsername(@RequestParam("username") String username);
}
