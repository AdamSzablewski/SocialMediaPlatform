package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecClient {
    @GetMapping("/security/validate/owner/facilityID/{facilityId}/user/{userEmail}")
    boolean isOwner(@PathVariable Long facilityId,@PathVariable String userEmail);

    @GetMapping("/security/validate/employee/facilityID/{facilityId}/user/{userEmail}")
    boolean isEmployee(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/user/{userId}/{userMail}")
    boolean isUser(@PathVariable Long userId,@PathVariable String userMail);
}
