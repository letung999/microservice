package com.example.organizationservice.service.contract;

import com.example.organizationservice.dto.OrganizationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrganizationService {
    ResponseEntity<String> saveOrganization(OrganizationDto organizationDto);

    ResponseEntity<OrganizationDto> getOrganizationByCode(String code);
}
