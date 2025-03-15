package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.ShortenUrlRequest;
import com.projects.shortify_backend.dto.response.ShortenUrlResponse;
import com.projects.shortify_backend.services.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/authenticated")
@RequiredArgsConstructor
public class ShortenUrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortUrl(@RequestBody @Valid ShortenUrlRequest shortenUrlRequest){

        log.info("Url object : {}",shortenUrlRequest);

        var code = urlService.shortenUrl(shortenUrlRequest);

        return new ResponseEntity<>(code, HttpStatus.OK);

    }


}
