package com.adamszablewski.exceptions;

public class NotAuthorizedException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Not authorized for this action";

    public NotAuthorizedException(String message) {
        super(message);
    }
    public NotAuthorizedException() {
        super(DEFAULT_MESSAGE);
    }
}
