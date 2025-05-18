package com.projects.shortify_backend.exception.handler;

import com.projects.shortify_backend.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String >> urlHandler(MethodArgumentNotValidException exception){

        var response = new HashMap<String, String>();

        exception.getAllErrors().forEach(error ->{
            response.put("error",error.getObjectName());
            response.put("message", error.getDefaultMessage());
        });

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<Map<String, String>> forbiddenAccessExceptionHandler(ForbiddenAccessException exception){

        var response = new HashMap<String, String>();
        response.put("error", "ForbiddenAccessException");
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){

        var response = new HashMap<String, String>();
        response.put("error", "UsernameNotFoundException");
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException exception){

        var response = new HashMap<String, String>();
        response.put("error", "UnknownException");
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> generalExceptionHandler(Exception ex) {

        var response = new HashMap<String, String>();
        response.put("error", "UnknownException");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFoundExceptionHandler(UserNotFoundException ex) {

        var response = new HashMap<String, String>();
        response.put("error", "UserNotFoundException");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Map<String, String>> urlNotFoundExceptionHandler(UrlNotFoundException ex) {

        var response = new HashMap<String, String>();
        response.put("error", "UrlNotFoundException");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<Map<String, String>> handleUrlExpiredException(UrlExpiredException ex) {
        var response = new HashMap<String, String>();
        response.put("error", "UrlExpiredException");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.GONE);
    }


}
