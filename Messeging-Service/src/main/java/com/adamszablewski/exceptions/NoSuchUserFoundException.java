package com.adamszablewski.exceptions;

public class NoSuchUserFoundException extends RuntimeException{
    public NoSuchUserFoundException(){}
    public NoSuchUserFoundException(String message){
        super(message);
    }
}
