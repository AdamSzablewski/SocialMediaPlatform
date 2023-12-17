package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecClient {
    @GetMapping("/security/validate/owner/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isOwner(@PathVariable Long facilityId,@PathVariable String userEmail);

    @GetMapping("/security/validate/employee/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isEmployee(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/user/{userId}/{userMail}")
    RestResponseDTO<Boolean> isUser(@PathVariable Long userId,@PathVariable String userMail);
}
