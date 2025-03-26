package com.projects.shortify_backend.exception.custom;

public class UrlExpiredException extends RuntimeException {
    public UrlExpiredException(String message) {
        super(message);
    }

  @Override
  public String toString(){
    return "UrlExpiredException";
  }
}
