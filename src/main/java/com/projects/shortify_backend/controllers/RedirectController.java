package com.projects.shortify_backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RedirectController {

    @GetMapping("/{pathVariable}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String pathVariable){

        log.info("Path Variable : {}",pathVariable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
