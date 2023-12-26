package com.adamszablewski.exceptions;

import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public class CustomExceptionHandler {

    public static ResponseEntity<?> handleException(Throwable ex) {
        ex.printStackTrace();


        if (ex instanceof FileNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else if (ex instanceof NotAuthorizedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
