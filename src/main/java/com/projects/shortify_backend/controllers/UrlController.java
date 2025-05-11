package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.*;
import com.projects.shortify_backend.services.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticated")
public class UrlController {

    private final UrlService urlService;

    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    @GetMapping("/all-urls")
    public ResponseEntity<List<UrlResponseDto>> getAllUrls(){
        return new ResponseEntity<>(urlService.getAllUrls(),HttpStatus.OK);
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlResponseDto> shortenUrl(@Valid @RequestBody ShortenUrlRequestDto request){

        return new ResponseEntity<>(urlService.shortenUrl(request),HttpStatus.CREATED);

    }

    @PutMapping("/update-url")
    public ResponseEntity<UpdateUrlResponseDto> updateUrl(@Valid @RequestBody UpdateUrlRequestDto request){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-url")
    public ResponseEntity<DeleteUrlResponseDto> deleteUrl(@RequestBody DeleteUrlRequestDto request){

        return new ResponseEntity<>(urlService.deleteUrl(request), HttpStatus.OK);
    }

}
