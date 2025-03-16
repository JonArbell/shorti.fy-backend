package com.projects.shortify_backend.exception.custom;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString(){
        return "EmailNotFoundException";
    }
}
