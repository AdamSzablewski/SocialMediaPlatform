package com.adamszablewski.exceptions;

public class NoSuchConversationFoundException extends RuntimeException{
    public NoSuchConversationFoundException(){}
    public NoSuchConversationFoundException(String message){
        super(message);
    }
}
