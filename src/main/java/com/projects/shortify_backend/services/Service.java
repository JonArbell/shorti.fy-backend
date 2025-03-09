package com.projects.shortify_backend.services;

import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.dto.SendUrl;
import com.projects.shortify_backend.repository.Repo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@org.springframework.stereotype.Service
@Log4j2
@RequiredArgsConstructor
public class Service {

    private final Repo repository;

    public String shortenUrl(SendUrl sendUrl){

        log.info("URL : {}",sendUrl);
        return Base62Encoder.encode(500);
    }


}
