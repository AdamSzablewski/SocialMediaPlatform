package com.adamszablewski.service;

import com.adamszablewski.config.TwilioConfig;
import com.adamszablewski.events.OtpEvent;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TwilioOTPService {
    private TwilioConfig twilioConfig;

    public void sendOTPForPasswordReset(OtpEvent otpEvent) {

        PhoneNumber to = new PhoneNumber(otpEvent.getPhoneNumer());
        PhoneNumber from = new PhoneNumber(twilioConfig.getSenderNumber());
        String otp = otpEvent.getOtp();
        String otpMessage = "Dear customer your one time password is ##" + otp + "##. Use it to reset your password";
        Message.creator(to, from, otpMessage).create();
    }
}
