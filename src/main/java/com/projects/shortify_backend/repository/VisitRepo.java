package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.entities.Visit;
import com.projects.shortify_backend.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepo extends JpaRepository<Visit, Long> {

    Optional<Visit> findByUrlAndVisitor(URL url, Visitor visitor);
    Optional<Visit> findAllByUrl(URL url);
}
