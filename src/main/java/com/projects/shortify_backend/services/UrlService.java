package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.*;
import com.projects.shortify_backend.dto.dashboard.DashboardResponseDto;
import com.projects.shortify_backend.dto.dashboard.RecentVisitsResponseDto;
import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.exception.custom.EmailNotFoundException;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.UserRepo;
import com.projects.shortify_backend.repository.VisitRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo urlRepo;

    private final UserRepo userRepo;

    private final AuthenticationService authenticationService;

    @Transactional
    public List<UrlResponseDto> getAllUrls(){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new EmailNotFoundException("Cannot find this email."));

        return urlRepo.findAllDtoByUser(user);
    }


    @Transactional
    public ShortenUrlResponseDto shortenUrl(ShortenUrlRequestDto request){

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new EmailNotFoundException(""));

        var saved = urlRepo.save(
                Url.builder()
                        .originalUrl(request.getOriginalUrl())
                        .isActive(true)
                        .maxClick(1)
                        .totalClicked(0)
                        .shortUrl(Base62Encoder.encode(request.getOriginalUrl().length()))
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build()
        );

        log.info("Saved url : {}",saved.getShortUrl());

        return ShortenUrlResponseDto.builder()
                .originalUrl(saved.getOriginalUrl())
                .shortUrl(saved.getShortUrl())
                .success(true)
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional
    public DeleteUrlResponseDto deleteUrl(DeleteUrlRequestDto request) {

        var user = userRepo.findByEmail(authenticationService.getCurrentUserEmail())
                .orElseThrow(() -> new EmailNotFoundException("User not found"));

        var url = urlRepo.findByIdAndUser(request.getId(), user)
                .orElseThrow(() -> new UrlNotFoundException("URL not found or doesn't belong to the user"));

        urlRepo.delete(url);

        return new DeleteUrlResponseDto(true);
    }


    @Transactional
    public Url updateStatus(Url url){

        if(url.getTotalClicked().equals(url.getMaxClick())){
            url.setActive(false);
            return urlRepo.save(url);
        }

        return null;

    }
}
