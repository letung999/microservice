package com.example.organizationservice.repository;

import com.example.organizationservice.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByCode(String code);
}
