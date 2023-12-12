package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "BOOKING")
public interface BookingServiceClient {

    @GetMapping("/facilities/owner/mail/by/id/{id}")
    RestResponseDTO<String> getOwnerMailById(@PathVariable long id);
    @GetMapping("/facilities/id/{facilityId}/employees")
    RestResponseDTO<String> getEmployeesForFacility(@PathVariable long facilityId);
    @GetMapping("/appointments/id/{appointmentId}/client")
    RestResponseDTO<String> getClientForAppointment(@PathVariable long appointmentId);
    @GetMapping("/appointments/id/{appointmentId}")
    RestResponseDTO<Long> getFacilityIdForAppointment(@PathVariable long appointmentId);
}
