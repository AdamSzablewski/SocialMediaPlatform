package com.adamszablewski.exceptions;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {
    public static ResponseEntity<RestResponseDTO<?>> handleException(Throwable ex) {
        RestResponseDTO<?> responseBody = RestResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        if (ex instanceof NoSuchUserFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }   else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
