package com.micky.employeeservice.service.impl;

import com.micky.employeeservice.client.APIClient;
import com.micky.employeeservice.dtos.APIResponseDto;
import com.micky.employeeservice.dtos.DepartmentDto;
import com.micky.employeeservice.dtos.EmployeeDto;
import com.micky.employeeservice.dtos.OrganizationDto;
import com.micky.employeeservice.exception.ExistingItemException;
import com.micky.employeeservice.exception.ResourceNotFoundException;
import com.micky.employeeservice.mapper.EmployeeMapper;
import com.micky.employeeservice.repository.EmployeeRepository;
import com.micky.employeeservice.service.contract.IEmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;
//    private APIClient apiClient;
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

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public ResponseEntity<APIResponseDto> detail(Long id) {
        logger.info("inside getEmployeeBydId() method");
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
        var departmentDtoResponse = webClient.get()
                .uri("http://localhost:8080/department/detail/" + employeeEntity.get().getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        var organizationDtoResponse = webClient.get()
                .uri("http://localhost:8083/organization/detail/" + employeeEntity.get().getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();
//        var departmentDtoResponse = apiClient
//                .getDepartmentByDepartmentCode(employeeEntity.get().getDepartmentCode())
//                .getBody();
        var resultData = new APIResponseDto();
        resultData.setEmployeeDto(employeeDtoResponse);
        resultData.setDepartmentDto(departmentDtoResponse);
        resultData.setOrganizationDto(organizationDtoResponse);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    public ResponseEntity<APIResponseDto> getDefaultDepartment(Long id, Exception exception) {
        logger.info("inside getDefaultDepartment() method");
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
