package com.example.organizationservice.service.impl;

import com.example.organizationservice.constant.StatusConstant;
import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.exception.DuplicateDataException;
import com.example.organizationservice.exception.ResourceNotFoundException;
import com.example.organizationservice.mapper.OrganizationMapper;
import com.example.organizationservice.repository.OrganizationRepository;
import com.example.organizationservice.service.contract.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationMapper organizationMapper = OrganizationMapper.INSTANCE;

    @Override
    public ResponseEntity<String> saveOrganization(OrganizationDto organizationDto) {
        var organizationEntity = organizationRepository.findByCode(organizationDto.getCode());
        if (organizationEntity.isPresent()) {
            throw new DuplicateDataException("organization", "code", organizationDto.getCode());
        }
        var resultData = organizationMapper.OrganizationDtoToOrganization(organizationDto);
        organizationRepository.save(resultData);
        return new ResponseEntity<>(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrganizationDto> getOrganizationByCode(String code) {
        var organization = organizationRepository.findByCode(code);
        if (organization.isEmpty()) {
            throw new ResourceNotFoundException("organization", "code", code);
        }
        var resultData = organizationMapper.OrganizationToOrganizationDto(organization.get());
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
