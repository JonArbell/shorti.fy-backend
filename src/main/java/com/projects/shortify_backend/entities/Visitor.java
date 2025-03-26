package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VISITORS")
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

    @ToString.Exclude
    @OneToMany(orphanRemoval = true, mappedBy = "visitor", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

}
