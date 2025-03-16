package com.projects.shortify_backend.exception.custom;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString(){
        return "UrlNotFoundException";
    }
}
