package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long> {

    List<Url> findAllByUser(User user);

    Optional<Url> findByIdAndUser(Long id, User user);

    @Query("SELECT v From Url v where v.user = :user ORDER BY v.createdAt DESC")
    List<Url> findAllUrlByUserSortedByCreatedAtDesc(@Param("user") User user);

    @Query("SELECT v FROM Url v WHERE v.shortUrl = :shortUrl")
    Optional<Url> findByShortUrl(@Param("shortUrl") String shortUrl);

    List<Url> findAllByExpirationDateBeforeAndIsActiveIsTrue(LocalDate date);
}
