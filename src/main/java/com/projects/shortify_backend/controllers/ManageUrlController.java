package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.MyUrlsResponse;
import com.projects.shortify_backend.services.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/authenticated")
@RestController
@RequiredArgsConstructor
public class ManageUrlController {

    private final UrlService urlService;

    @GetMapping("/my-urls")
    public ResponseEntity<List<MyUrlsResponse>> myUrls(){

        var listOfUrls = urlService.getAllUrls();

        return new ResponseEntity<>(listOfUrls, HttpStatus.OK);

    }

    @DeleteMapping("/urls/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUrl(@PathVariable Long id){

        log.info("ID Delete : {}",id);

        var delete = urlService.deleteUrl(id);

        return new ResponseEntity<>(Map.of("message",delete),HttpStatus.OK);

    }

    @GetMapping("/urls/{id}")
    public ResponseEntity<Map<String, String>> getUrl(@PathVariable Long id){

        log.info("ID get : {}",id);

//        var delete = urlService.deleteUrl(id);

        return new ResponseEntity<>(Map.of("message","getUrl"),HttpStatus.OK);

    }

}
