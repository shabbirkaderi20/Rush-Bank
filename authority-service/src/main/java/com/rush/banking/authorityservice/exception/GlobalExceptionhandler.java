package com.rush.banking.authorityservice.exception;

import com.rush.banking.authorityservice.constant.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler(NoAuthorityFoundException.class)
    public ResponseEntity<Object> handleNoAuthorityFoundException(NoAuthorityFoundException exception) {
        return new ResponseEntity<>(Constants.AUTHORITY_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
