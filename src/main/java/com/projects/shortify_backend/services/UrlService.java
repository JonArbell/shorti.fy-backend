package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.UrlResponseDto;
import com.projects.shortify_backend.exception.custom.EmailNotFoundException;
import com.projects.shortify_backend.repository.UrlRepo;
import com.projects.shortify_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepo urlRepo;

    private final UserRepo userRepo;

    private final AuthenticationService authenticationService;

    @Transactional
    public List<UrlResponseDto> getAllUrls(){

        var email = authenticationService.getCurrentUserEmail();

        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Cannot find this email."));

        return urlRepo.findAllDtoByUser(user);
    }

}
