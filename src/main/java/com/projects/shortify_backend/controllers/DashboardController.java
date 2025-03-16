package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.DashboardResponseDTO;
import com.projects.shortify_backend.services.DashboardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/authenticated")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> dashboard(){

        var response = dashboardService.dashboard();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
