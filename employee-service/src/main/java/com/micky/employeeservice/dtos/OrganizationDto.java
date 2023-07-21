package com.micky.employeeservice.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrganizationDto {
    private Long id;
    private String name;
    private String description;
    private String code;
    private LocalDateTime createdData;
}
