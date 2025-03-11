package com.projects.shortify_backend.services;

import com.projects.shortify_backend.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                return (User) userDetails;
            }
        }

        return null;
    }


}
