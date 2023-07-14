package com.example.departmentservice.repository;

import com.example.departmentservice.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    Optional<Department> findByDepartmentCode(String departmentCode);

    List<Department> findAllByDepartmentCodeIn(List<String> departmentCodes);
}
