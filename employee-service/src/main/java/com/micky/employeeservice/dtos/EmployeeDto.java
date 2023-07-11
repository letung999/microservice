package com.micky.employeeservice.dtos;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private String departmentCode;
}
