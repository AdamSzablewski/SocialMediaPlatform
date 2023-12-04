package com.adamszablewski.feign;

import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @PostMapping("/tokens/validate")
    RestResponseDTO<Boolean> validateToken(@RequestBody String token);
}
