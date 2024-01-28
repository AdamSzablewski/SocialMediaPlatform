package com.adamszablewski.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class OtpEvent {
    String otp;
    String phoneNumer;
    long userId;
}
