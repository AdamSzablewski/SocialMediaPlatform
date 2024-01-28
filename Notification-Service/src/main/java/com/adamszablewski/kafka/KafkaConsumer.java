package com.adamszablewski.kafka;

import com.adamszablewski.events.OtpEvent;
import com.adamszablewski.service.TwilioOTPService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adamszablewski.kafka.KafkaConfig.OTP_EVENT_TOPIC;
@AllArgsConstructor
@Component
public class KafkaConsumer {
    private final TwilioOTPService twilioOTPService;

    @KafkaListener(topics = OTP_EVENT_TOPIC, groupId = "notification-group")
    public void consumeOTPMessage(OtpEvent otpEvent){
        twilioOTPService.sendOTPForPasswordReset(otpEvent);
    }
}