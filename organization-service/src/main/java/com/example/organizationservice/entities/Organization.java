package com.example.organizationservice.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "createdDate")
    @CreationTimestamp
    private LocalDateTime createdData;
}
