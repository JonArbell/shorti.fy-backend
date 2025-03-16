package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.RedirectResponseDTO;
import com.projects.shortify_backend.entities.Visitor;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
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

        var findShortUrl = urlRepository.findByShortUrl(local+shortUrl);

        if(findShortUrl.isPresent()){

            var url = findShortUrl.get();

            log.info("Is Url expired ? : {}",url.isExpired());

            if(url.isExpired()){
                return RedirectResponseDTO
                        .builder()
                        .responseMessage("url expired")
                        .build();
            }

            var totalClicked = url.getNumberOfClicked()+1;

            url.setNumberOfClicked(totalClicked);

            if(url.getMaxClicked().equals(totalClicked))
                url.setExpired(true);

            var savedUrl = urlRepository.save(url);

            log.info("Saved URL : {}",savedUrl);

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

            var ipAddress = getClientIp(request);

            var findFirst = visitorRepo.findAllByUrl(url).stream()
                    .filter(visitor -> visitor.getIpAddress() != null && visitor.getIpAddress().equals(ipAddress))
                    .findFirst();

            if(findFirst.isPresent()){ // Update the existing visitor

                var visitor = findFirst.get();

                visitor.setNumberOfVisit(visitor.getNumberOfVisit()+1);
                visitor.setDevice(deviceType);

                var saved = visitorRepo.save(visitor);

                log.info("Saved Visitor : {}",saved);

                return RedirectResponseDTO.builder()
                        .maskedIpAddress(saved.getIpAddress())
                        .originalUrl(saved.getUrl().getOriginalUrl())
                        .numberOfVisit(saved.getNumberOfVisit())
                        .device(saved.getDevice())
                        .location(saved.getLocation())
                        .responseMessage("Update visitor")
                        .build();
            }

            var newVisitor = visitorRepo.save( // Create new visitor
                    Visitor.builder()
                            .device(deviceType)
                            .url(url)
                            .ipAddress(ipAddress)
                            .location("Unknown location")
                            .numberOfVisit(1L)
                            .build()
            );

            log.info("New Visitor : {}",newVisitor);

            return RedirectResponseDTO.builder()
                    .maskedIpAddress(newVisitor.getIpAddress())
                    .originalUrl(newVisitor.getUrl().getOriginalUrl())
                    .numberOfVisit(newVisitor.getNumberOfVisit())
                    .device(newVisitor.getDevice())
                    .location(newVisitor.getLocation())
                    .responseMessage("Saved new visitor")
                    .build();

        }

        throw new UrlNotFoundException("URL Not Found");
    }

}
