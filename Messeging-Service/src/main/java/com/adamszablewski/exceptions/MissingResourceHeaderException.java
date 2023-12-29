package com.adamszablewski.exceptions;

public class MissingResourceHeaderException extends RuntimeException{
    public MissingResourceHeaderException(){}
    public MissingResourceHeaderException(String message){
        super(message);
    }
}
