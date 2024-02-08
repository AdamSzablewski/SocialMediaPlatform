package com.adamszablewski.util;


import com.adamszablewski.feign.SecurityServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SecurityUtil {

    private final SecurityServiceClient securityServiceClient;

    public boolean isUser(long userId, String token) {
        long userIdFromToken = securityServiceClient.extractUserIdFromToken(token);
        return userId == userIdFromToken;
    }


}
