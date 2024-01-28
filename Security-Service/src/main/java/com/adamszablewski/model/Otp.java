package com.adamszablewski.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Otp {
    public static final int OTP_TIME_MAX = 5;
    private long userId;
    private String otp;
    private LocalDateTime dateTime;
}
