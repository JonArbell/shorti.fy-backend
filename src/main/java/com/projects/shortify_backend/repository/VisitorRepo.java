package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findByIpAddress(String ipAddress);
}
