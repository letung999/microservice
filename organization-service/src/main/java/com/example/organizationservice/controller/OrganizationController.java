package com.example.organizationservice.controller;


import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.service.impl.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "organization")
public class OrganizationController {
    @Autowired
    private OrganizationServiceImpl organizationService;

    @PostMapping("/save-organization")
    public ResponseEntity<String> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        return organizationService.saveOrganization(organizationDto);
    }

    @GetMapping("detail/{code}")
    public ResponseEntity<OrganizationDto> getOrganizationDetail(@PathVariable String code) {
        return organizationService.getOrganizationByCode(code);
    }
}
