package com.projects.shortify_backend.exception.custom;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString(){
        return "UserNotFoundException";
    }
}
