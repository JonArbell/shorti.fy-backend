package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {

    @Query("""
        SELECT v 
        FROM Visit v 
        WHERE v.url.user.email = :email 
        ORDER BY v.visitedAt DESC
    """)
    List<Visit> findTop5RecentVisitsByeEmail(@Param("email") String email, Pageable pageable);



}
