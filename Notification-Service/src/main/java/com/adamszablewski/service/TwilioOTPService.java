package com.adamszablewski.service;

import com.adamszablewski.config.TwilioConfig;
import com.adamszablewski.dto.OtpStatus;
import com.adamszablewski.dto.PasswordResetRequestDto;
import com.adamszablewski.dto.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.bouncycastle.asn1.cmp.Challenge;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@AllArgsConstructor
public class TwilioOTPService {

    private TwilioConfig twilioConfig;
    private Map<String, String> otpMap;

    public Mono<PasswordResetResponseDto> sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto){

        PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String otp = generateOTP();
        String otpMessage = "Dear customer your one time password is ##"+otp+"##. Use it to reset your password";
        Message message = Message
                .creator(to,
                        from,
                        otpMessage)
                .create();
        PasswordResetResponseDto passwordResetResponseDto = PasswordResetResponseDto.builder()
                .status(OtpStatus.DELIVERED)
                .message(otpMessage)
                .build();
        otpMap.put(passwordResetRequestDto.getUsername(), otp);
        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOTP(String userInputOtp, String userName){
        if (userInputOtp.equals(otpMap.get(userName))){
            return Mono.just("Valid");
        }else {
            return Mono.error(new IllegalArgumentException("Invalid OTP"));
        }
    }

    private String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
