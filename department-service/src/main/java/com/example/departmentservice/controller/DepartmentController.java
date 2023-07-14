package com.example.departmentservice.controller;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @PostMapping(value = "/add-department")
    public ResponseEntity<String> add(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<DepartmentDto>> all(@RequestParam(name = "pageIndex") Integer pageIndex,
                                                   @RequestParam(name = "pageSize") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        return departmentService.all(pageRequest);
    }

    @GetMapping(value = "/detail/{departmentCode}")
    public ResponseEntity<DepartmentDto> getDepartmentByDepartmentCode(@PathVariable String departmentCode) {
        return departmentService.getDepartmentByDepartmentCode(departmentCode);
    }

    @PostMapping(value = "/getDepartmentListByDepartmentCodes")
    public ResponseEntity<List<DepartmentDto>> getDepartmentListByDepartmentCodes(@RequestBody List<String> departmentCodes){
        return departmentService.getDepartmentListByDepartmentCode(departmentCodes);
    }
}
