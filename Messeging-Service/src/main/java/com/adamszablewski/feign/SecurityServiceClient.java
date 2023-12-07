package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecurityServiceClient {
    @GetMapping("/security/validate/owner/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isOwner(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/employee/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isEmployee(@PathVariable Long facilityId,@PathVariable String userEmail);
    @GetMapping("/security/validate/user/{userId}/{userEmail}")
    RestResponseDTO<Boolean> isUser(@PathVariable Long userId,@PathVariable String userEmail);
}
