package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.services.RedirectUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectUrlService redirectUrlService;

    @GetMapping("/{pathVariable}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String pathVariable,
                                                        HttpServletRequest request){

        log.info("Path Variable : {}",pathVariable);

        var originalUrl = redirectUrlService.getOriginalUrlByShortUrl(pathVariable, request);

        if ("expired".equals(originalUrl)) {
            // You can dynamically pass the original short URL or any info
            var redirectTo = "http://localhost:4200/expired-url?url=" + pathVariable;

            return ResponseEntity.status(HttpStatus.FOUND) // or HttpStatus.SEE_OTHER if you want
                    .header("Location", redirectTo)
                    .build();
        }

        return ResponseEntity.status(HttpStatus.FOUND.value()).header("Location", originalUrl)
                .build();
    }

    @GetMapping("/api/validate-url/{code}")
    public ResponseEntity<Map<String, Boolean>> validateUrl(@PathVariable String code){

        log.info("Code : {}",code);

        return new ResponseEntity<>(redirectUrlService.validateUrl(code), HttpStatus.OK);
    }

}
