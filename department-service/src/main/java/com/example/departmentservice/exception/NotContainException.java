package com.example.departmentservice.exception;

import com.example.departmentservice.constant.StatusConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotContainException extends RuntimeException {
    private String fieldName;
    private String fieldValue;

    public NotContainException(String fieldValue, String fieldName) {
        super(String.format("%s" + StatusConstant.INVALID_DATA + "s%s", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
