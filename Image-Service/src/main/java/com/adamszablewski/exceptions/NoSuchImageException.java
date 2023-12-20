package com.adamszablewski.exceptions;

public class NoSuchImageException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Not authorized for this action";

    public NoSuchImageException(String message) {
        super(message);
    }
    public NoSuchImageException() {
        super(DEFAULT_MESSAGE);
    }
}
