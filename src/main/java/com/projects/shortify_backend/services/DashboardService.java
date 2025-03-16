package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.DashboardResponseDTO;
import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UrlRepo urlRepository;
    private final UserService userService;

    @Transactional
    public DashboardResponseDTO dashboard(){

        var allUrl = urlRepository.findAllByUserId(userService.getCurrentUser().getId());

        var expiredUrlLinks = allUrl.stream().filter(URL::isExpired).count();

        var activeUrlLinks = allUrl.stream().filter(url -> !url.isExpired()).count();

        var mostClickedUrl = allUrl.stream()
                .filter(url -> url.getNumberOfClicked() > 0L)
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
}
