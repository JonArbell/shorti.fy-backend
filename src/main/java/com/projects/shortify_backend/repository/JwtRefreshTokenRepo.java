package com.projects.shortify_backend.repository;

import com.projects.shortify_backend.entities.JwtRefreshToken;
import com.projects.shortify_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRefreshTokenRepo extends JpaRepository<JwtRefreshToken, Long> {

    @Modifying // ✅ tells JPA this is a modification query (not just a read operation).
    @Query("DELETE FROM JwtRefreshToken t WHERE t.user = :user") // ✅ Force immediate deletion using a direct SQL query.
    void deleteByUser(User user);

}
