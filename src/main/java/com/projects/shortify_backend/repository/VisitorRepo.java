package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitorRepo extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByIpAddress(String ipAddress);

}
