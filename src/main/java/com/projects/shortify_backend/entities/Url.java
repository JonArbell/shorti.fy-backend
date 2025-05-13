package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "URLS")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private boolean isActive;

    private Integer totalClicked;

    @Column(nullable = false)
    private Integer maxClick;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private List<Visit> visits;

}
