package com.adamszablewski.kafka;

import com.adamszablewski.events.OtpEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaMessagePublisher {
    private KafkaTemplate<Object, Object> template;
    public void sendOTPEventMessage(OtpEvent otpEvent) {
        template.send(KafkaConfig.OTP_EVENT_TOPIC, otpEvent);
    }
}
