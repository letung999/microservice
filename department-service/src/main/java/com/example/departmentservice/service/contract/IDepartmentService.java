package com.example.departmentservice.service.contract;

import com.example.departmentservice.dto.DepartmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDepartmentService {
    ResponseEntity<String> addDepartment(DepartmentDto departmentDto);

    ResponseEntity<List<DepartmentDto>> all(Pageable pageable);

    ResponseEntity<DepartmentDto> getDepartmentByDepartmentCode(String departmentCode);
}
