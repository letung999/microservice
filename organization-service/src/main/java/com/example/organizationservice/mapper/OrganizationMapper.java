package com.example.organizationservice.mapper;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entities.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    Organization OrganizationDtoToOrganization(OrganizationDto organizationDto);

    OrganizationDto OrganizationToOrganizationDto(Organization organization);

    List<OrganizationDto> OrganizationListToOrganizationDtoList(List<Organization> organizations);
}
