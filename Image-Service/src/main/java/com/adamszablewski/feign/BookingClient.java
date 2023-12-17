package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BOOKING")
public interface BookingClient {

    @GetMapping("/facilities/{facilityId}/image/{imageId}")
    void addPortfolioImage(@PathVariable String imageId,@PathVariable long facilityId);
}
