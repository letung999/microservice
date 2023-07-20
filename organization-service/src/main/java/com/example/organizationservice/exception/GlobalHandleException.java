package com.example.organizationservice.exception;

import com.example.organizationservice.constant.StatusConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public static ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        var errorDetail = new ErrorDetails(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                ex.getMessage(),
                StatusConstant.DATA_IS_NOT_FOUND);
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public static ResponseEntity<ErrorDetails> handleDuplicateDataException(DuplicateDataException ex, WebRequest webRequest) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                ex.getMessage(),
                StatusConstant.DATA_IS_EXIST
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
