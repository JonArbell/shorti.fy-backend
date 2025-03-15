package com.projects.shortify_backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetUrl {

    @GetMapping("/{url}")
    public String getUrl(@PathVariable String url) {
        log.info("Get URL : {}", url);

        return "Panis";
    }

}
