package com.adamszablewski.exceptions;

import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomExceptionHandler {

    public static ResponseEntity<RestResponseDTO<?>> handleException(Throwable ex) {
        ex.printStackTrace();
        RestResponseDTO<?> responseBody = RestResponseDTO.builder()
                .error(ex.getMessage())
                .build();

        if (ex instanceof FileNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        else if (ex instanceof NotAuthorizedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }

        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
