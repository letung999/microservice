package com.example.organizationservice.exception;

import com.example.organizationservice.constant.StatusConstant;

public class DuplicateDataException extends RuntimeException {
    private String resources;
    private String fieldName;
    private String fieldValue;

    public DuplicateDataException(String resources, String fieldName, String fieldValue) {
        String.format("%s " + StatusConstant.DATA_IS_EXIST + "with %s: %s", resources, fieldName, fieldValue);
        this.resources = resources;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
