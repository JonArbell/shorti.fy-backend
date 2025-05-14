package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.UrlResponseDto;
import com.projects.shortify_backend.dto.dashboard.DashboardResponseDto;
import com.projects.shortify_backend.dto.dashboard.RecentVisitsResponseDto;
import com.projects.shortify_backend.repository.VisitRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UrlService urlService;

    private final AuthenticationService authenticationService;

    private final VisitRepo visitRepo;

    @Transactional
    public DashboardResponseDto dashboard(){

        var allUrls = urlService.getAllUrls();

        var activeUrls = allUrls.stream().peek(url -> {
            if(url.getTotalClicked().equals(url.getNumberOfClicks())){
                url.setActive(false);
            }
        }).filter(UrlResponseDto::isActive).count();

        var mostClickedUrl = allUrls.stream()
                .filter(url -> url.getTotalClicked() != null && url.getTotalClicked() > 0)
                .max(Comparator.comparingInt(UrlResponseDto::getTotalClicked))
                .map(UrlResponseDto::getOriginalUrl).orElse("None");

        return DashboardResponseDto.builder()
                .totalUrlLinks((long) allUrls.size())
                .activeUrls(activeUrls)
                .expiredUrls((long) allUrls.size() - activeUrls)
                .mostClickedUrl(mostClickedUrl)
                .build();
    }

    @Transactional
    public List<RecentVisitsResponseDto> recentVisits(){

        return visitRepo.findTop5RecentVisitsByeEmail(authenticationService.getCurrentUserEmail(), PageRequest.of(0,5))
                .stream()
                .map(visit -> {

                    var checkStatus = urlService.updateStatus(visit.getUrl());

                    return RecentVisitsResponseDto.builder()
                            .id(visit.getId())
                            .originalUrl(visit.getUrl().getOriginalUrl())
                            .shortenedUrl("http://localhost:8080/"+visit.getUrl().getShortUrl())
                            .isActive(checkStatus.isActive())
                            .ipAddress(visit.getVisitor().getIpAddress())
                            .numberOfClicks(visit.getNumberOfClicks())
                            .build();
                })
                .collect(Collectors.toList());

    }



}
