package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.MyUrlResponseDTO;
import com.projects.shortify_backend.exception.custom.UnauthorizedAccessException;
import com.projects.shortify_backend.exception.custom.UrlNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ManagerUrlService {

    private final UrlRepo urlRepository;
    private final UserService userService;

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
    public List<MyUrlResponseDTO> getAllUrls() {
        return urlRepository
                .findAllByUserId(userService.getCurrentUser().getId())
                .stream()
                .map(url -> MyUrlResponseDTO.builder()
                        .id(url.getId())
                        .shortUrl(url.getShortUrl())
                        .originalUrl(url.getOriginalUrl())
                        .numberOfClicked(url.getNumberOfClicked())
                        .expiryDate(url.getExpiryDate())
                        .isExpired(url.isExpired())
                        .maxClicked(url.getMaxClicked())
                        .build())
                .toList();

    }

    @Transactional
    public MyUrlResponseDTO getUrl(Long id) {

        var findUrl = urlRepository.findById(id);

        if(findUrl.isEmpty()){
            throw new UrlNotFoundException("Url not found.");
        }


        return findUrl.filter(url -> url.getUser().getId().equals(userService.getCurrentUser().getId()))
                .map(url -> MyUrlResponseDTO
                        .builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .isExpired(url.isExpired())
                        .expiryDate(url.getExpiryDate())
                        .numberOfClicked(url.getNumberOfClicked())
                        .maxClicked(url.getMaxClicked())
                        .build()
                )
                .orElseThrow(() -> new UnauthorizedAccessException("You are not authorized to access this URL."));

    }



}
