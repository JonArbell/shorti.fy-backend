package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.SendUrl;
import com.projects.shortify_backend.services.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticated")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortUrl(@RequestBody @Valid SendUrl sendUrl){

        var code = urlService.shortenUrl(sendUrl);

        return new ResponseEntity<>(code, HttpStatus.CREATED);
    }

}
