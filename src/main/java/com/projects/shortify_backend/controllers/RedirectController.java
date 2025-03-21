package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.RedirectResponseDTO;
import com.projects.shortify_backend.services.RedirectUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RedirectController {

    private final RedirectUrlService redirectUrl;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<RedirectResponseDTO> redirectUrl(@PathVariable String shortUrl,
                                      @RequestHeader("User-Agent") String userAgent,
                                      HttpServletRequest request) {
        log.info("Get URL : {}", shortUrl);

        var response = redirectUrl.redirectUrl(shortUrl, request,userAgent);

        if("url not found".equals(response.getResponseMessage()))
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        if("url expired".equals(response.getResponseMessage()))
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, response.getOriginalUrl())
                .build();
    }

}
