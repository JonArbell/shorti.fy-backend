package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.UrlResponseDto;
import com.projects.shortify_backend.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticated")
public class UrlController {

    private final UrlService urlService;

    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    @GetMapping("/all-urls")
    public ResponseEntity<List<UrlResponseDto>> allUrls(){
        return new ResponseEntity<>(urlService.getAllUrls(),HttpStatus.OK);
    }

}
