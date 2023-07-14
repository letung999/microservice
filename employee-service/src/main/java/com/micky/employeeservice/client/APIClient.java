package com.micky.employeeservice.client;

import com.micky.employeeservice.dtos.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE")
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClient {
    @GetMapping(value = "department/detail/{departmentCode}")
    ResponseEntity<DepartmentDto> getDepartmentByDepartmentCode(@PathVariable String departmentCode);
}
