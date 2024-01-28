package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/user/password/hashed")
    String getHashedPassword(@RequestParam("userEmail") String userEmail);
    @GetMapping("/user/{userId}/{userEmail}")
    boolean isUser(@PathVariable long userId,@PathVariable String userEmail);
    @GetMapping("user/convert")
    Long getUserIdFromUsername(@RequestParam("email") String username);
    @GetMapping("user/phoneNumber")
    String getPhoneNumberForUser(@RequestParam("userId") long userId);
}
