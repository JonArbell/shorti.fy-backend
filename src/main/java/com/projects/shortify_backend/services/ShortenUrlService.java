package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.request.ShortenUrlRequestDTO;
import com.projects.shortify_backend.dto.response.ShortenUrlResponse;
import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortenUrlService {

    private final UserService userService;
    private final UrlRepo urlRepository;


    @Transactional
    public ShortenUrlResponse shortenUrl(ShortenUrlRequestDTO shortenUrlRequestDTO){

        log.info("URL : {}", shortenUrlRequestDTO);

        var shortUrl = Base62Encoder.encode(userService.getCurrentUser().getId());

        var local = "http://localhost:8080/";

        var savedUrl = urlRepository.save(
                URL.builder()
                        .originalUrl(shortenUrlRequestDTO.getUrl())
                        .maxClicked(5L)
                        .expiryDate(Instant.now().plusSeconds(500))
                        .isExpired(false)
                        .numberOfClicked(0L)
                        .shortUrl(local+shortUrl)
                        .user(userService.getCurrentUser())
                        .build()
        );

        return ShortenUrlResponse.builder()
                .id(savedUrl.getId())
                .shortUrl(savedUrl.getShortUrl())
                .originalUrl(savedUrl.getOriginalUrl())
                .isExpired(savedUrl.isExpired())
                .numberOfClicked(savedUrl.getNumberOfClicked())
                .expiryDate(savedUrl.getExpiryDate())
                .maxClicked(savedUrl.getMaxClicked())
                .build();

    }

}
