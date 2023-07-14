package com.example.departmentservice.exception;

import com.example.departmentservice.constant.StatusConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequireFieldException extends RuntimeException {
    private String filedName;
    private String fieldValue;

    public MissingRequireFieldException(String filedName, String fieldValue) {
        super(String.format("%s" + StatusConstant.FIELD_REQUIRED + " %s", filedName, fieldValue));
        this.fieldValue = fieldValue;
        this.filedName = filedName;
    }

}
