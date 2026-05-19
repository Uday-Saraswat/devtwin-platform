package com.uday.authservice.exception;

import com.uday.authservice.dto.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RegisterResponse<Object>> handleRuntimeException( RuntimeException ex){
        RegisterResponse<Object> response =
                RegisterResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );

    }
}
