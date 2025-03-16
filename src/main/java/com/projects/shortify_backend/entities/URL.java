package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "URLS")
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    @Column(updatable = false, unique = true)
    private String shortUrl;

    private Instant expiryDate;

    private boolean isExpired;

    private Long numberOfClicked;

    private Long maxClicked;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ToString.Exclude
    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<Visitor> visitors;

}
