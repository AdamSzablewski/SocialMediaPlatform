package com.adamszablewski.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    private String phoneNumber;
    @Size(min = 2 , message = "Password must have 2 characters")
    private String password;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Region is required")
    private String region;
    @NotBlank(message = "City is required")
    private String city;
}
