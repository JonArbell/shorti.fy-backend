package com.projects.shortify_backend.exception.handler;

import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
import com.projects.shortify_backend.exception.custom.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> generalExceptionHandler(Exception ex) {

        return new ResponseEntity<>(Map.of(ex.getClass().getSimpleName(),ex.getLocalizedMessage()),HttpStatus.BAD_REQUEST);
    }

}
