package com.adamszablewski.service;


import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exception.InvalidCredentialsException;
import com.adamszablewski.exception.MissingFeignValueException;
import com.adamszablewski.feign.BookingServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.util.JwtUtil;
import com.adamszablewski.util.TokenGenerator;
import com.adamszablewski.util.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecurityService {


    private final PasswordEncoder passwordEncoder;
    private final BookingServiceClient bookingServiceClient;
    private final UserServiceClient userServiceClient;
    private final JwtUtil jwtUtil;
    private final UserValidator userValidator;
    private final TokenGenerator tokenGenerator;
    public String validateUser(LoginDto loginDto) {
        String response = userServiceClient.getHashedPassword(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), response)){
            throw new InvalidCredentialsException();
        }
        return generateToken(loginDto.getEmail());
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public boolean validateOwner(long facilityId, String userMail) {
       return userValidator.isOwner(facilityId, userMail);
    }

    public Boolean validateEmployee(long facilityId, String userMail) {
        return userValidator.isEmployee(facilityId, userMail);
    }

    public Boolean validateUserEmail(long userId, String userMail) {
        return userValidator.isUser(userId, userMail);
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public long extractUserIdFromToken(String token) {
        return jwtUtil.getUserIdFromToken(token);
    }

    public String generateToken(String email) {
        long userId = userServiceClient.getUserIdFromUsername(email);
        return tokenGenerator.generateToken(userId);
    }
}
