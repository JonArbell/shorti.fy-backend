package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.UrlResponseDTO;
import com.projects.shortify_backend.dto.response.UrlsResponseDTO;
import com.projects.shortify_backend.services.ManagerUrlService;
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

    private final ManagerUrlService managerUrlService;

    @DeleteMapping("/urls/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUrl(@PathVariable Long id){

        log.info("ID Delete : {}",id);

        var delete = managerUrlService.deleteUrl(id);

        return new ResponseEntity<>(Map.of("message",delete),HttpStatus.OK);

    }

    @GetMapping("/my-urls")
    public ResponseEntity<List<UrlsResponseDTO>> myUrls(){

        var listOfUrls = managerUrlService.getAllUrls();

        return new ResponseEntity<>(listOfUrls, HttpStatus.OK);

    }

    @GetMapping("/urls/{id}")
    public ResponseEntity<UrlResponseDTO> getUrl(@PathVariable Long id){

        log.info("ID get : {}",id);

        var getUrl = managerUrlService.getUrl(id);

        return new ResponseEntity<>(getUrl,HttpStatus.OK);

    }

}
