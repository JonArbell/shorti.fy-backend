package com.projects.shortify_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "URLS")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    @Column(updatable = false, unique = true)
    private String shortUrl;

    private Instant expiryDate;

    private boolean isExpired;

    private Integer numberOfClicked;

    private Instant timeStamp;

    private Integer maxClicked;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @OneToMany(orphanRemoval = true, mappedBy = "url", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

}
