package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping
    Map<String, Long> getIdsForMails(List<String> recipientMail);
}
