package com.uday.github_analysis_service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {
    
    private boolean success;

    private String message;

    private int status;

    private LocalDateTime timestamp;
}
