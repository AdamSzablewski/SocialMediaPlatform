package com.adamszablewski.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecurityServiceClient {
    @GetMapping("/security/validate/owner/facilityID/{facilityId}/user/{userEmail}")
    boolean isOwner(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/employee/facilityID/{facilityId}/user/{userEmail}")
    boolean isEmployee(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/user/{userId}/{userEmail}")
    boolean isUser(@PathVariable Long userId,@PathVariable String userEmail);
    @GetMapping("/security/hash")
    String hashPassword(@RequestParam("password") String password);
}
