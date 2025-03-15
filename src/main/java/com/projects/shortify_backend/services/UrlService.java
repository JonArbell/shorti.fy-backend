package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.DashboardResponseDTO;
import com.projects.shortify_backend.dto.response.MyUrlsResponse;
import com.projects.shortify_backend.dto.response.ShortenUrlResponse;
import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.dto.request.ShortenUrlRequest;
import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo repository;
    private final UserService userService;

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest shortenUrlRequest){

        log.info("URL : {}", shortenUrlRequest);

        var shortUrl = Base62Encoder.encode(userService.getCurrentUser().getId());

        var savedUrl = repository.save(
                            URL.builder()
                        .originalUrl(shortenUrlRequest.getUrl())
                        .maxClicked(100L)
                        .expiryDate(Instant.now().plusSeconds(500))
                        .isExpired(false)
                        .numberOfClicked(100L)
                        .shortUrl(shortUrl)
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

    @Transactional
    public DashboardResponseDTO dashboard(){

        var allUrl = repository.findAllByUserId(userService.getCurrentUser().getId());

        var expiredUrlLinks = allUrl.stream().filter(URL::isExpired).count();

        var activeUrlLinks = allUrl.stream().filter(url -> !url.isExpired()).count();

        var mostClickedUrl = allUrl.stream()
                .max(Comparator.comparingLong(URL::getNumberOfClicked))
                .map(URL::getOriginalUrl)
                .orElse("None");

        return DashboardResponseDTO
                .builder()
                .overallTotalUrlLinks((long) allUrl.size())
                .totalExpiredUrlLinks(expiredUrlLinks)
                .totalActiveUrlLinks(activeUrlLinks)
                .mostClickedUrl(mostClickedUrl)
                .build();

    }

    @Transactional
    public List<MyUrlsResponse> getAllUrls() {
        return repository
                .findAllByUserId(userService.getCurrentUser().getId())
                .stream()
                .map(url -> MyUrlsResponse.builder()
                        .id(url.getId())
                        .shortUrl(url.getShortUrl())
                        .originalUrl(url.getOriginalUrl())
                        .numberOfClicked(url.getNumberOfClicked())
                        .expiryDate(url.getExpiryDate())
                        .isExpired(url.isExpired())
                        .maxClicked(url.getMaxClicked())
                        .build())
                .toList();

    }

}
