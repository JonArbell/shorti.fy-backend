package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.*;
import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.exception.custom.UrlExpiredException;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.exception.custom.UserNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.UserRepo;
import com.projects.shortify_backend.repository.VisitRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo urlRepo;
    private final UserRepo userRepo;

    private final VisitRepo visitRepo;
    private final AuthenticationService authenticationService;

    @Transactional
    public List<UrlResponseDto> getAllUrls(){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated or does not exist."));

        return urlRepo.findAllUrlByUserSortedByCreatedAtDesc(user)
                .stream()
                .map(url -> UrlResponseDto.builder()
                        .maxClick(url.getMaxClick())
                        .id(url.getId())
                        .shortUrl(url.getShortUrl())
                        .isActive(url.isActive())
                        .originalUrl(url.getOriginalUrl())
                        .totalClicked(url.getTotalClicked())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public ShortenUrlResponseDto shortenUrl(ShortenUrlRequestDto request){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated or does not exist."));

        var saved = urlRepo.save(
                Url.builder()
                        .originalUrl(request.getOriginalUrl())
                        .isActive(true)
                        .maxClick(request.getMaxClick())
                        .password("".equals(request.getPassword()) ? null : request.getPassword())
                        .expirationDate(request.getExpirationDate())
                        .totalClicked(0)
                        .shortUrl(Base62Encoder.encode(request.getOriginalUrl().length()))
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build()
        );

        log.info("Saved url : {}",saved.getShortUrl());

        return ShortenUrlResponseDto.builder()
                .originalUrl(saved.getOriginalUrl())
                .maxClick(saved.getMaxClick())
                .shortUrl(saved.getShortUrl())
                .expirationDate(saved.getExpirationDate())
                .success(true)
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional
    public DeleteUrlResponseDto deleteUrl(Long id) {

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated or does not exist."));

        var url = urlRepo.findByIdAndUser(id, user)
                .orElseThrow(() -> new UrlNotFoundException("URL not found or doesn't belong to the user."));

        urlRepo.delete(url);

        return new DeleteUrlResponseDto(true);
    }

    @Transactional
    public UpdateUrlResponseDto updateUrl(UpdateUrlRequestDto update, Long id){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated or does not exist."));

        var url = urlRepo.findByIdAndUser(id, user)
                .orElseThrow(() -> new UrlNotFoundException("URL not found or doesn't belong to the user."));

        if(!url.isActive())
            throw new UrlExpiredException("This url is already expired.");

        var oldUrl = url.getOriginalUrl();

        url.setOriginalUrl(update.getOriginalUrl());
        url.setExpirationDate(update.getExpirationDate());
        url.setPassword(update.getPassword());
        url.setMaxClick(update.getMaxClick());

        var updatedUrl = urlRepo.save(url);

        return UpdateUrlResponseDto.builder()
                .oldLongUrl(oldUrl)
                .expirationDate(updatedUrl.getExpirationDate())
                .maxClick(updatedUrl.getMaxClick())
                .updatedLongUrl(updatedUrl.getOriginalUrl())
                .message("success")
                .build();

    }

    @Transactional
    public UrlResponseDto getUrlById(Long id){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated or does not exist."));

        var url = urlRepo.findByIdAndUser(id, user)
                .orElseThrow(() -> new UrlNotFoundException("URL not found or doesn't belong to the user."));

        var maxClick = url.getMaxClick();

        var visitors = visitRepo.findAllByUrl(url)
                .stream()
                .map(visit -> VisitorResponseDto.builder()
                        .ipAddress(visit.getVisitor().getIpAddress())
                        .deviceType(visit.getVisitor().getDeviceType())
                        .latestVisitedAt(visit.getLatestVisitedAt())
                        .build())
                .collect(Collectors.toList());

        return UrlResponseDto.builder()
                .maxClick(maxClick == 0 ? null : maxClick)
                .password(url.getPassword())
                .expirationDate(url.getExpirationDate())
                .shortUrl(url.getShortUrl())
                .originalUrl(url.getOriginalUrl())
                .id(url.getId())
                .isActive(url.isActive())
                .totalClicked(url.getTotalClicked())
                .visitorResponseDtoList(visitors)
                .build();
    }

    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void deactivateExpiredUrls() {

        List<Url> expired = urlRepo.findAllByExpirationDateBeforeAndIsActiveIsTrue(LocalDate.now());
        for (var url : expired) {
            url.setActive(false);
        }

        urlRepo.saveAll(expired);
    }
}
