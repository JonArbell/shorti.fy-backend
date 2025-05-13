package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.services.RedirectUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectUrlService redirectUrlService;

    @GetMapping("/{pathVariable}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String pathVariable,
                                                        HttpServletRequest request){

        log.info("Path Variable : {}",pathVariable);

        var originalUrl = redirectUrlService.getOriginalUrlByShortUrl(pathVariable, request);

        if ("expired".equals(originalUrl)) {
            return ResponseEntity.status(HttpStatus.GONE)
                    .body("This short URL has expired or reached its click limit.");
        }

        return ResponseEntity.status(HttpStatus.FOUND.value()).header("Location", originalUrl)
                .build();
    }

}
