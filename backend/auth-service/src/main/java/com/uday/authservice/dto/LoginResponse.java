package com.uday.authservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    @Schema(description = "JWT Token",example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

}
