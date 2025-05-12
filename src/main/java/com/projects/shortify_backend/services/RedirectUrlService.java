package com.projects.shortify_backend.services;

import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectUrlService {

    private final UrlRepo urlRepo;

    public String getOriginalUrlByShortUrl(String shortUrl){
        return urlRepo.findByShortUrl(shortUrl)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found."));
    }

}
