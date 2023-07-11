package com.micky.employeeservice.controller;

import com.micky.employeeservice.dtos.APIResponseDto;
import com.micky.employeeservice.dtos.EmployeeDto;
import com.micky.employeeservice.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    public EmployeeServiceImpl employeeService;

    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> all(@RequestParam(value = "pageIndex") Integer pageIndex,
                                                 @RequestParam(value = "pageSize") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        return employeeService.all(pageRequest);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<APIResponseDto> detail(@PathVariable Long id){
        return employeeService.detail(id);
    }
}
