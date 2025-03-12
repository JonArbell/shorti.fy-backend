package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.JwtRefreshToken;
import com.projects.shortify_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRefreshTokenRepo extends JpaRepository<JwtRefreshToken, Long> {

    void deleteByUser(User user);

}
