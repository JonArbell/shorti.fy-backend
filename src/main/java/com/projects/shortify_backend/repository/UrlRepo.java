package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<URL, Long> {
    List<URL> findAllByUserId(Long id);
    Optional<URL> findByShortUrl(String shortUrl);
}
