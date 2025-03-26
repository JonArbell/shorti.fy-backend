package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.UrlResponseDTO;
import com.projects.shortify_backend.dto.response.UrlsResponseDTO;
import com.projects.shortify_backend.dto.response.base.VisitorBaseResponseDTO;
import com.projects.shortify_backend.exception.custom.UnauthorizedAccessException;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.VisitRepo;
import com.projects.shortify_backend.repository.VisitorRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ManagerUrlService {

    private final UrlRepo urlRepository;
    private final VisitorRepo visitorRepo;
    private final UserService userService;
    private final VisitRepo visitRepo;

    @Transactional
    public String deleteUrl(Long id){

        var findUrl = urlRepository.findById(id);

        if(findUrl.isEmpty())
            throw new RuntimeException("");

        if(!findUrl.get().getUser().getId().equals(userService.getCurrentUser().getId()))
            throw new RuntimeException("");

        urlRepository.delete(findUrl.get());

        return "success";

    }

    @Transactional
    public List<UrlsResponseDTO> getAllUrls() {
        return urlRepository
                .findAllByUserId(userService.getCurrentUser().getId())
                .stream()
                .map(url ->
                        UrlsResponseDTO
                            .builder()
                            .id(url.getId())
                            .shortUrl(url.getShortUrl())
                            .originalUrl(url.getOriginalUrl())
                            .numberOfClicked(url.getNumberOfClicked())
                            .expiryDate(url.getExpiryDate())
                            .isExpired(url.isExpired())
                            .maxClicked(url.getMaxClicked())
                            .build()
                )
                .collect(Collectors.toList());

    }

    @Transactional
    public UrlResponseDTO getUrl(Long id) {

        var findUrl = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("Url not found."));

        var urlUserId = findUrl.getUser().getId();

        var currentUserId = userService.getCurrentUser().getId();

        if(!currentUserId.equals(urlUserId))
            throw new UnauthorizedAccessException("You are not authorized to access this URL.");

        //Ip, location, device, id of visitor,

        var visits = visitRepo.findAllByUrl(findUrl)
                .map(visit -> VisitorBaseResponseDTO
                        .builder()
                        .id(visit.getVisitor().getId())
                        .location(visit.getVisitor().getLocation())
                        .device(visit.getVisitor().getDevice())
                        .ipAddress(visit.getVisitor().getIpAddress())
                        .numberOfClicked(visit.getNumberOfVisit())
                        .build())
                .stream()
                .collect(Collectors.toList());

        return UrlResponseDTO
                .builder()
                .id(findUrl.getId())
                .originalUrl(findUrl.getOriginalUrl())
                .shortUrl(findUrl.getShortUrl())
                .isExpired(findUrl.isExpired())
                .expiryDate(findUrl.getExpiryDate())
                .numberOfClicked(findUrl.getNumberOfClicked())
                .maxClicked(findUrl.getMaxClicked())
                .visitors(visits)
                .build();

    }

}
