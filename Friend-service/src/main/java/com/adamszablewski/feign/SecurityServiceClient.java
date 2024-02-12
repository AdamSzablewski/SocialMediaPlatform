package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecurityServiceClient {
    @GetMapping("security/token")
    long extractUserIdFromToken(@RequestParam("token") String token);
}
