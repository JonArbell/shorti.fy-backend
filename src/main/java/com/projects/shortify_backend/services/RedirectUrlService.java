package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.RedirectResponseDTO;
import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.entities.Visit;
import com.projects.shortify_backend.entities.Visitor;
import com.projects.shortify_backend.exception.custom.UrlExpiredException;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.VisitRepo;
import com.projects.shortify_backend.repository.VisitorRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedirectUrlService {

    private final VisitorRepo visitorRepo;
    private final UrlRepo urlRepository;
    private final VisitRepo visitRepo;

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }

        // In case of multiple proxies, the client IP might be a comma-separated list
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0];
        }

        return clientIp;
    }



    @Transactional
    public RedirectResponseDTO redirectUrl(String shortUrl, HttpServletRequest request, String userAgent){

        var local = "http://localhost:8080/";

        var url = urlRepository.findByShortUrl(local+shortUrl)
                .orElseThrow(()-> new UrlNotFoundException("URL Not Found"));

        if(url.isExpired())
            throw new UrlExpiredException("The requested URL has expired.");

        url.setNumberOfClicked(url.getNumberOfClicked()+1);

        if(url.getMaxClicked().equals(url.getNumberOfClicked()))
            url.setExpired(true);

        urlRepository.save(url);

        var ipAddress = getClientIp(request);

        var visitor = getOrSaveNewVisitor(userAgent, ipAddress);

        var visit = visitRepo.findByUrlAndVisitor(url, visitor)
                .orElseGet(() -> visitRepo.save(
                        Visit.builder()
                                .url(url)
                                .visitor(visitor)
                                .numberOfVisit(0L)
                                .build()
                ));

        visit.setNumberOfVisit(visit.getNumberOfVisit()+1);

        var savedVisit = visitRepo.save(visit);

        return RedirectResponseDTO.builder()
                .originalUrl(savedVisit.getUrl().getOriginalUrl())
                .device(savedVisit.getVisitor().getDevice())
                .location("Unknown location")
                .numberOfVisit(savedVisit.getUrl().getNumberOfClicked())
                .maskedIpAddress(savedVisit.getVisitor().getIpAddress())
                .build();
    }

    private Visitor getOrSaveNewVisitor(String userAgent, String ipAddress){

        final String deviceType;

        if (userAgent.contains("Mobi")) {
            deviceType = "Mobile Device";
        } else if (userAgent.contains("Tablet")) {
            deviceType = "Tablet";
        } else if (userAgent.contains("Windows") || userAgent.contains("Macintosh") || userAgent.contains("Linux")) {
            deviceType = "Desktop or Laptop";
        }else {
            deviceType = "Unknown Device";
        }

        return visitorRepo.findByIpAddress(ipAddress)
                .orElseGet(() -> visitorRepo.save( // Create new visitor
                        Visitor.builder()
                                .device(deviceType)
                                .ipAddress(ipAddress)
                                .location("Unknown location")
                                .build()
                        )
                );
    }

}
