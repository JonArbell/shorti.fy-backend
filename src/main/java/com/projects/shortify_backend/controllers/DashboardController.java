package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.dashboard.DashboardResponseDto;
import com.projects.shortify_backend.dto.dashboard.RecentVisitsResponseDto;
import com.projects.shortify_backend.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticated")
public class DashboardController {

    private final UrlService urlService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDto> dashboard(){

        return new ResponseEntity<>(urlService.dashboard(),HttpStatus.OK);
    }

    @GetMapping("/recent-visits")
    public ResponseEntity<List<RecentVisitsResponseDto>> recentVisitors(){

        return new ResponseEntity<>(urlService.recentVisits(),HttpStatus.OK);
    }

}
