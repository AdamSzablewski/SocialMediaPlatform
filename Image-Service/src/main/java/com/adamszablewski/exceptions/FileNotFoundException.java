package com.adamszablewski.exceptions;

public class FileNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Failed to establish a connection with the remote service";

    public FileNotFoundException(String message) {
        super(message);
    }
    public FileNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
