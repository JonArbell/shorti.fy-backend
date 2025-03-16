package com.projects.shortify_backend.exception.custom;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    @Override
    public String toString(){
      return "UnauthorizedAccessException";
    }

}
