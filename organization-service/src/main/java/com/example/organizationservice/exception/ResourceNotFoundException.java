package com.example.organizationservice.exception;


import com.example.organizationservice.constant.StatusConstant;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException {
    private String resources;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resources, String fieldName, String fieldValue) {
        String.format("%s" + StatusConstant.DATA_IS_NOT_FOUND + "%s with value: %s", resources, fieldName, fieldValue);
        this.resources = resources;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
