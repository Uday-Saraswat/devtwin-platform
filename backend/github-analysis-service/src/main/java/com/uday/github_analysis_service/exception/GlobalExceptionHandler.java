package com.uday.github_analysis_service.exception;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //HANDLE RESOURCE NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
                                                                   HttpServletRequest request) {

        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .success(false)
                        .error("Resource Not Found")
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // HANDLE VALIDATION EXCEPTION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        ApiErrorResponse response = ApiErrorResponse.builder().success(false).error("Validation Error")
                .message(message).status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI()).timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // HANDLE GITHUB API EXCEPTION
    @ExceptionHandler(GithubApiException.class)
    public ResponseEntity<ApiErrorResponse> handleGithubApiException(GithubApiException ex, HttpServletRequest request) {
        ApiErrorResponse response = ApiErrorResponse.builder().success(false).error("GitHub API Error")
                .message(ex.getMessage()).status(HttpStatus.BAD_GATEWAY.value())
                .path(request.getRequestURI()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    // HANDLE FEIGN EXCEPTION
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiErrorResponse> handleFeignException(FeignException ex, HttpServletRequest request) {
        ApiErrorResponse response = ApiErrorResponse.builder().success(false).error("External API Error")
                .message("GitHub service unavailable").status(HttpStatus.BAD_GATEWAY.value())
                .path(request.getRequestURI()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    //HANDLE GENERIC EXCEPTION
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .success(false)
                        .error("Internal Server Error")
                        .message(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
