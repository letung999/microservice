package com.example.departmentservice.service.impl;

import com.example.departmentservice.constant.StatusConstant;
import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.exception.ExistingItemException;
import com.example.departmentservice.exception.MissingRequireFieldException;
import com.example.departmentservice.exception.NotContainException;
import com.example.departmentservice.exception.ResourceNotFoundException;
import com.example.departmentservice.mapper.DepartmentMapper;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.service.contract.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

    @Override
    public ResponseEntity<String> addDepartment(DepartmentDto departmentDto) {
        var departmentEntity = departmentRepository.findByDepartmentCode(departmentDto.getDepartmentCode());
        if (!departmentEntity.isEmpty()) {
            throw new ExistingItemException("Department", "DepartmentCode", departmentDto.getDepartmentCode());
        }
        var entity = departmentMapper.departmentDtoToDepartment(departmentDto);
        departmentRepository.save(entity);
        return new ResponseEntity<>(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> all(Pageable pageable) {
        var departmentPages = departmentRepository.findAll(pageable);
        var data = departmentPages.getContent();
        if (data.size() == 0) {
            return new ResponseEntity<>(new ArrayList<DepartmentDto>(), HttpStatus.OK);
        }
        var resultData = data.stream().map(
                department -> {
                    var departmentDto = departmentMapper.departmentToDepartmentDto(department);
                    return departmentDto;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartmentByDepartmentCode(String departmentCode) {
        var department = departmentRepository.findByDepartmentCode(departmentCode);
        if (department.isEmpty()) {
            throw new ResourceNotFoundException("department", "departmentCode", departmentCode.hashCode());
        }
        var resultData = departmentMapper.departmentToDepartmentDto(department.get());
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getDepartmentListByDepartmentCode(List<String> departmentCodes) {
        if (departmentCodes.isEmpty()) {
            throw new MissingRequireFieldException("departmentCodes", departmentCodes.toString());
        }

        var departments = departmentRepository.findAllByDepartmentCodeIn(departmentCodes);
        if (departmentCodes.isEmpty()) {
            throw new ResourceNotFoundException("department", "departmentObject", departments.hashCode());
        }

        var departmentCodeList = departments.stream().map(x -> {
            var codes = x.getDepartmentCode();
            return codes;
        }).collect(Collectors.toList());

        var checkExisted = departmentCodes.stream().allMatch(x -> departmentCodeList.contains(x));
        if (!checkExisted) {
            throw new NotContainException("departmentCode", departmentCodes.toString());
        }

        var resultData = departments.stream().map(x -> {
            var departmentDto = departmentMapper.departmentToDepartmentDto(x);
            return departmentDto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
