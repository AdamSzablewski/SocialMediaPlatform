package com.adamszablewski.util;

import com.adamszablewski.model.Otp;
import com.adamszablewski.repository.OtpRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@AllArgsConstructor
public class OtpManager {

    private final OtpRepository otpRepository;

    public boolean validateOTP(String oneTimePassword, long userId){
        Otp otp = otpRepository.findByOtp(oneTimePassword)
                .orElseThrow(EntityNotFoundException::new);
        if (userId != otp.getUserId()){
            throw new NotAuthorizedException("Wrong user");
        }
        return checkExceedsTime(otp);
    }
    public boolean checkExceedsTime(Otp otp){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime previousTime = otp.getDateTime();
        long minutesDifference = ChronoUnit.MINUTES.between(currentTime, previousTime);
        if (minutesDifference > Otp.OTP_TIME_MAX){
            throw new NotAuthorizedException("time limit exceeded");
        }
        return true;
    }

    public String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
