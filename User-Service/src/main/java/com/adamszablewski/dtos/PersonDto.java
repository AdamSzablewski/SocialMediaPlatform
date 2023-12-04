package com.adamszablewski.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PersonDto {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate birthDate;
}
