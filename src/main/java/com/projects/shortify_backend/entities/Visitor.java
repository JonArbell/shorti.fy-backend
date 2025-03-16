package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ipAddress;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String device;

    @Column(nullable = false)
    private Long numberOfVisit;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private URL url;
}
