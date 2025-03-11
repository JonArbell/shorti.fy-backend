package com.projects.shortify_backend.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "EmailAlreadyExistsException";
    }

}
