package com.micky.employeeservice.dtos;

import lombok.Data;

@Data
public class APIResponseDto {
    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;
}
