package com.adamszablewski.util;


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

        return securityServiceClient.isOwner(facilityId, userEmail);
    }
    public boolean isUser(long user, String email){
        System.out.println(user+"  "+email);

        return securityServiceClient.isUser(user, email);
    }
    public boolean isEmployee(long facilityId, String userEmail){

        return securityServiceClient.isEmployee(facilityId, userEmail);
    }


}
