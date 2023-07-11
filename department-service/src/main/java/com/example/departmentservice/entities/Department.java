package com.example.departmentservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department", nullable = false, length = 255)
    private String department;

    @Column(name = "departmentDescription", nullable = false, length = 255)
    private String departmentDescription;

    @Column(name = "departmentCode", nullable = false, length = 255, unique = true)
    private String departmentCode;
}
