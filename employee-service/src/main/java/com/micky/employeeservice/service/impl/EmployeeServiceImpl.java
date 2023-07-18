package com.micky.employeeservice.service.impl;

import com.micky.employeeservice.client.APIClient;
import com.micky.employeeservice.dtos.APIResponseDto;
import com.micky.employeeservice.dtos.DepartmentDto;
import com.micky.employeeservice.dtos.EmployeeDto;
import com.micky.employeeservice.exception.ExistingItemException;
import com.micky.employeeservice.exception.ResourceNotFoundException;
import com.micky.employeeservice.mapper.EmployeeMapper;
import com.micky.employeeservice.repository.EmployeeRepository;
import com.micky.employeeservice.service.contract.IEmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
//    @Autowired
//    private RestTemplate restTemplate;

    //    @Autowired
//    private WebClient webClient;
    private APIClient apiClient;
    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public ResponseEntity<String> addEmployee(EmployeeDto employeeDto) {
        var employeeEntity = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeEntity.isPresent()) {
            throw new ExistingItemException("user", "email", employeeDto.getEmail());
        }
        var entity = employeeMapper.employeeDtoToEmployee(employeeDto);
        employeeRepository.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> all(Pageable pageable) {
        var employeePages = employeeRepository.findAll(pageable);
        var employees = employeePages.getContent();
        if (employees.size() == 0) {
            return new ResponseEntity<>(new ArrayList<EmployeeDto>(), HttpStatus.OK);
        }
        var resultData = employees.stream().map(
                employee -> {
                    var employeeDto = employeeMapper.employeeToEmployeeDto(employee);
                    return employeeDto;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public ResponseEntity<APIResponseDto> detail(Long id) {
        var employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", Math.toIntExact(id));
        }
        var employeeDtoResponse = employeeMapper.employeeToEmployeeDto(employeeEntity.get());

        //call api to get department by departmentCode

        /**
         * use restemplate to call API from other services
         */
//        ResponseEntity<DepartmentDto> responseDepartmentDto = restTemplate
//                .getForEntity("http://localhost:8080/department/detail/" + employeeEntity.get().getDepartmentCode(), DepartmentDto.class);
//        var departmentDtoResponse = responseDepartmentDto.getBody();

        /**
         * use webclient to call API from other service
         */
//        var departmentDtoResponse = webClient.get()
//                .uri("http://localhost:8080/department/detail/" + employeeEntity.get().getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();
        var departmentDtoResponse = apiClient
                .getDepartmentByDepartmentCode(employeeEntity.get().getDepartmentCode())
                .getBody();
        var resultData = new APIResponseDto();
        resultData.setEmployeeDto(employeeDtoResponse);
        resultData.setDepartmentDto(departmentDtoResponse);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    public ResponseEntity<APIResponseDto> getDefaultDepartment(Long id, Exception exception) {
        var employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", Math.toIntExact(id));
        }
        var employeeDtoResponse = employeeMapper.employeeToEmployeeDto(employeeEntity.get());

        var departmentDtoResponse = new DepartmentDto();
        departmentDtoResponse.setDepartment("");
        departmentDtoResponse.setDepartmentDescription("");
        departmentDtoResponse.setDepartmentCode("");

        var resultData = new APIResponseDto();
        resultData.setEmployeeDto(employeeDtoResponse);
        resultData.setDepartmentDto(departmentDtoResponse);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
