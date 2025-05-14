package com.projects.shortify_backend.services;

import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.entities.Visit;
import com.projects.shortify_backend.entities.Visitor;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.VisitRepo;
import com.projects.shortify_backend.repository.VisitorRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedirectUrlService {

    private final UrlRepo urlRepo;

    private final VisitorRepo visitorRepo;

    private final VisitRepo visitRepo;

    @Transactional
    public String getOriginalUrlByShortUrl(String shortUrl, HttpServletRequest request){

        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        var findVisitor = visitorRepo.findByIpAddress(ip).orElseGet(() ->
                visitorRepo.save(
                        Visitor.builder()
                                .deviceType(userAgent)
                                .ipAddress(ip)
                                .build()
                )
        );

        log.info("IP Address : {}", findVisitor.getIpAddress());

        var findUrl = urlRepo.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found."));

        var numberOfClicks = findUrl.getTotalClicked();

        var isExpired = numberOfClicks + 1 > findUrl.getMaxClick();

        if(isExpired){
            findUrl.setActive(false);
            urlRepo.save(findUrl);
        }
        else{
            findUrl.setTotalClicked(numberOfClicks + 1);
            urlRepo.save(findUrl);

            visitRepo.findByUrlAndVisitor(findUrl, findVisitor)
                    .ifPresentOrElse(visit ->{
                        visit.setNumberOfClicks(visit.getNumberOfClicks() + 1);

                        visitRepo.save(visit);
                    }, () ->{
                        visitRepo.save(
                                Visit.builder()
                                        .url(findUrl)
                                        .visitedAt(LocalDateTime.now())
                                        .numberOfClicks(1)
                                        .visitor(findVisitor)
                                        .build()
                        );
                    });

        }

        log.info("Is Expired : {}", !findUrl.isActive());

        log.info("URL Total Clicked : {}", findUrl.getTotalClicked());

        return isExpired ? "expired" : findUrl.getOriginalUrl();
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    public Map<String, Boolean> validateUrl(String code){

        var isUrlExpired = !urlRepo.findByShortUrl(code)
                .orElseThrow(() -> new UrlNotFoundException("Url not found."))
                .isActive();

        return Map.of("isExpired",isUrlExpired);

    }


}
