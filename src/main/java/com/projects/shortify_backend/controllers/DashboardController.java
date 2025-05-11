package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.DashboardRequestDto;
import com.projects.shortify_backend.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticated")
public class DashboardController {

    private final UrlService urlService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardRequestDto> dashboard(){

        return new ResponseEntity<>(urlService.dashboard(),HttpStatus.OK);
    }

}
