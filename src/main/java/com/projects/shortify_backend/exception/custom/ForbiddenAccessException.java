package com.projects.shortify_backend.exception.custom;

public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException(String message) {
        super(message);
    }

    @Override
    public String toString(){
      return "ForbiddenAccessException";
    }
}
