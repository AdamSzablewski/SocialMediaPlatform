package com.adamszablewski;

import com.adamszablewski.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class NotificationApplication {
    @Autowired
    private TwilioConfig twilioConfig;
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
    @PostConstruct
    public void initTwilio(){
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }
}