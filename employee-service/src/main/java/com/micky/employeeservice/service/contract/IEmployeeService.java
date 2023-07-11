package com.micky.employeeservice.service.contract;

import com.micky.employeeservice.dtos.APIResponseDto;
import com.micky.employeeservice.dtos.EmployeeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmployeeService {
    ResponseEntity<String> addEmployee(EmployeeDto employeeDto);

    ResponseEntity<List<EmployeeDto>> all(Pageable pageable);

    ResponseEntity<APIResponseDto> detail(Long id);
}
