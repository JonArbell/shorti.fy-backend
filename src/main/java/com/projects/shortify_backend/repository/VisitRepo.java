package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.Url;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.entities.Visit;
import com.projects.shortify_backend.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {

    @Query("""
        SELECT v 
        FROM Visit v 
        WHERE v.url.user.email = :email 
        ORDER BY v.visitedAt DESC
    """)
    List<Visit> findTop5RecentVisitsByeEmail(@Param("email") String email, Pageable pageable);

    Optional<Visit> findByUrlAndVisitor(Url url, Visitor visitor);

    List<Visit> findAllByUrlAndVisitor(Url url, Visitor visitor);

}
