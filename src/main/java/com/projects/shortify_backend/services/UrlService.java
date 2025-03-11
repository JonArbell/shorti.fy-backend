package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.DashboardResponseDTO;
import com.projects.shortify_backend.encoder.Base62Encoder;
import com.projects.shortify_backend.dto.SendUrl;
import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Comparator;

@Service
@Log4j2
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo repository;
    private final UserService userService;

    public String shortenUrl(SendUrl sendUrl){

        log.info("URL : {}",sendUrl);
        return Base62Encoder.encode(500);
    }

    public DashboardResponseDTO dashboard(){

        var allUrl = repository.findAllByUserId(userService.getCurrentUser().getId());

        var expiredUrlLinks = allUrl.stream().filter(URL::isExpired).count();

        var activeUrlLinks = allUrl.stream().filter(url -> !url.isExpired()).count();

        var mostClickedUrl = allUrl.stream()
                .max(Comparator.comparingLong(URL::getNumOfClicked))
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
