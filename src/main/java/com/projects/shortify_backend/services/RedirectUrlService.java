package com.projects.shortify_backend.services;
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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

        var ip = getClientIp(request);
        var userAgent = request.getHeader("User-Agent");

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

        var isAlreadyExpired = !findUrl.isActive();

        if(isAlreadyExpired) return "expired";

        var numberOfClicks = findUrl.getTotalClicked();

        findUrl.setTotalClicked(numberOfClicks + 1);

        if(findUrl.getTotalClicked().equals(findUrl.getMaxClick()) ||
                (findUrl.getExpirationDate() != null && LocalDate.now().isAfter(findUrl.getExpirationDate()))
        ) findUrl.setActive(false);

        urlRepo.save(findUrl);

        visitRepo.findByUrlAndVisitor(findUrl, findVisitor)
            .ifPresentOrElse(visit ->{
                visit.setNumberOfClicks(visit.getNumberOfClicks() + 1);
                visit.setLatestVisitedAt(LocalDateTime.now());
                visitRepo.save(visit);
            }, () -> visitRepo.save(
                    Visit.builder()
                            .url(findUrl)
                            .latestVisitedAt(LocalDateTime.now())
                            .numberOfClicks(1)
                            .visitor(findVisitor)
                            .build()
            ));


        log.info("Is Expired : {}", !findUrl.isActive());

        log.info("URL Total Clicked : {}", findUrl.getTotalClicked());

        return findUrl.getOriginalUrl();
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

        if(isUrlExpired) throw new UrlExpiredException("This url is already expired.");

        return Map.of("isExpired",false);

    }

}
