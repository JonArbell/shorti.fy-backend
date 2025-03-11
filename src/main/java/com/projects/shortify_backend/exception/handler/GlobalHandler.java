package com.projects.shortify_backend.exception.handler;

import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
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

        var errors = new HashMap<String, String>();
        exception.getAllErrors().forEach(error ->
                errors.put(error.getCode()+" Error",error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){

        return new ResponseEntity<>(Map.of(UsernameNotFoundException.class.getSimpleName(),exception.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException exception){

        return new ResponseEntity<>(Map.of(EmailAlreadyExistsException.class.getSimpleName(),exception.getMessage()),
                HttpStatus.FORBIDDEN);
    }

}
