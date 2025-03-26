package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.URL;
import com.projects.shortify_backend.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor, Long> {
    List<Visitor> findAllByUrl(URL url);
    Optional<Visitor> findByIpAddress(String ipAddress);
}
