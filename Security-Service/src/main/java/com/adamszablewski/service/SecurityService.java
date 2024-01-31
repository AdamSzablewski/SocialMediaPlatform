package com.adamszablewski.service;


import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.events.OtpEvent;
import com.adamszablewski.exception.InvalidCredentialsException;
import com.adamszablewski.feign.BookingServiceClient;
import com.adamszablewski.feign.UserServiceClient;
import com.adamszablewski.kafka.KafkaMessagePublisher;
import com.adamszablewski.model.Otp;
import com.adamszablewski.repository.OtpRepository;
import com.adamszablewski.util.JwtUtil;
import com.adamszablewski.util.OtpManager;
import com.adamszablewski.util.TokenGenerator;
import com.adamszablewski.util.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class SecurityService {


    private final PasswordEncoder passwordEncoder;
    private final BookingServiceClient bookingServiceClient;
    private final UserServiceClient userServiceClient;
    private final JwtUtil jwtUtil;
    private final UserValidator userValidator;
    private final TokenGenerator tokenGenerator;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final OtpRepository otpRepository;
    private final OtpManager otpManager;
    public String validateUser(LoginDto loginDto) {
        String response = userServiceClient.getHashedPassword(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), response)){
            throw new InvalidCredentialsException();
        }
        return generateTokenFromEmail(loginDto.getEmail());
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

    public String generateTokenFromEmail(String email) {
        long userId = userServiceClient.getUserIdFromUsername(email);
        return tokenGenerator.generateToken(userId);
    }
    public String generateToken(long userId){
        return tokenGenerator.generateToken(userId);
    }

    public void sendOTP(String phoneNumber, long userId) {
        String userPhoneNumber = userServiceClient.getPhoneNumberForUser(userId);
        if (!userPhoneNumber.equals(phoneNumber)){
            throw new NotAuthorizedException("Phone number does not match any account");
        }
        String oneTimePassword = otpManager.generateOTP();
        Otp otp = Otp.builder()
                .otp(oneTimePassword)
                .dateTime(LocalDateTime.now())
                .build();
        OtpEvent event = OtpEvent.builder()
                .otp(oneTimePassword)
                .phoneNumer(phoneNumber)
                .userId(userId)
                .build();
        otpRepository.save(otp);
        kafkaMessagePublisher.sendOTPEventMessage(event);
    }
    public String validateOTP(String oneTimePassword, long userId){
        Otp otp = otpRepository.findByOtp(oneTimePassword)
                .orElseThrow(EntityNotFoundException::new);
        boolean isValid =  otpManager.validateOTP(userId, otp);
        if (isValid){
            otpRepository.delete(otp);
            return generateToken(userId);
        }else {
            throw new NotAuthorizedException("validation failed");
        }
    }

}
