package com.adamszablewski.util;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exception.MissingFeignValueException;
import com.adamszablewski.feign.BookingServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class UserValidator {
    private final BookingServiceClient bookingServiceClient;
    private final UserServiceClient userServiceClient;
    public boolean isOwner(long facilityId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getOwnerMailById(facilityId);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        String ownerEmail = response.getValue();
        return ownerEmail.equals(userEmail);
    }

    public boolean isUser(long userId, String userEmail){
        RestResponseDTO<Boolean> response = userServiceClient.isUser(userId, userEmail);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        return response.getValue();

    }
    public boolean isEmployee(long facilityId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getEmployeesForFacility(facilityId);
        if (response.getValues() == null){
            throw new MissingFeignValueException();
        }
        Set<String> employeeMails = response.getValues();
        return employeeMails.stream()
                .anyMatch(mail -> mail.equals(userEmail));
    }
    public boolean isClient(long appointmentId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getClientForAppointment(appointmentId);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        String clientEmail = response.getValue();
        return clientEmail.equals(userEmail);
    }

    public boolean isOwnerOrEmployeeOrTheClient(long appointmentId, String usereEmail){
        RestResponseDTO<Long> response = bookingServiceClient.getFacilityIdForAppointment(appointmentId);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        long facilityId = response.getValue();
        return isClient(appointmentId, usereEmail) || isEmployee(facilityId, usereEmail)
                || isOwner(facilityId, usereEmail);
        //todo

    }
    public boolean isOwnerOrEmployee(long appointmentId, String userEmail){
        return isOwner(appointmentId, userEmail) || isEmployee(appointmentId, userEmail);
        //todo
    }
}
