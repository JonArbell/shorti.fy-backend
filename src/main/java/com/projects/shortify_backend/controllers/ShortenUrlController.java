package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.ShortenUrlRequestDTO;
import com.projects.shortify_backend.dto.response.ShortenUrlResponse;
import com.projects.shortify_backend.services.ShortenUrlService;
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

    private final ShortenUrlService shortenUrlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortUrl(@RequestBody @Valid ShortenUrlRequestDTO shortenUrlRequestDTO){


        var code = shortenUrlService.shortenUrl(shortenUrlRequestDTO);

        return new ResponseEntity<>(code, HttpStatus.OK);

    }

}
