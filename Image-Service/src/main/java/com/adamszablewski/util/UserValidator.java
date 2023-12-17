package com.adamszablewski.util;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exceptions.MissingFeignValueException;
import com.adamszablewski.feign.SecClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {

    private final SecClient securityServiceClient;
    public boolean isOwner(long facilityId, String userEmail){
        //return facility.getOwner().getUser().getEmail().equals(userEmail);
        RestResponseDTO<Boolean> response = securityServiceClient.isOwner(facilityId, userEmail);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        return response.getValue();
    }
    public boolean isUser(long user, String email){
        System.out.println(user+"  "+email);
        RestResponseDTO<Boolean> response = securityServiceClient.isUser(user, email);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }

        return response.getValue();
    }
    public boolean isEmployee(long facilityId, String userEmail){
        RestResponseDTO<Boolean> response = securityServiceClient.isEmployee(facilityId, userEmail);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
      return true;
    }


}
