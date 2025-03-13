package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.ShortenUrlRequest;
import com.projects.shortify_backend.dto.response.MyUrlsResponse;
import com.projects.shortify_backend.services.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authenticated")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortUrl(@RequestBody @Valid ShortenUrlRequest shortenUrlRequest){

        var code = urlService.shortenUrl(shortenUrlRequest);

        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @GetMapping("/my-urls")
    public ResponseEntity<List<MyUrlsResponse>> myUrls(){

        var listOfUrls = urlService.getAllUrls();

        return new ResponseEntity<>(listOfUrls, HttpStatus.OK);
    }

}
