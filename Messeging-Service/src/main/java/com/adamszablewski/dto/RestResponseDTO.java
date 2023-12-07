package com.adamszablewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RestResponseDTO<T>{
    private T value;
    private Set<T> values;
    private String error;
}

