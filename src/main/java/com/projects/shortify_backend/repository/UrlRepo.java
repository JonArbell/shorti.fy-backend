package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.dto.UrlResponseDto;
import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long> {

    List<Url> findAllByUser(User user);

    @Query("SELECT new com.projects.shortify_backend.dto.UrlResponseDto(u.shortUrl, u.originalUrl) from Url u where u" +
            ".user = :user")
    List<UrlResponseDto> findAllDtoByUser(@Param("user") User user);
}
