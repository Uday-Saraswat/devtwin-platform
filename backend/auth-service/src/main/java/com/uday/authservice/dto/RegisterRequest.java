package com.uday.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @Schema(description = "User full name", example = "Uday Saraswat")
    @NotBlank
    private String name;

    @Schema(description = "User email", example = "uday@gmail.com")
    @Email
    private String email;

    @Schema(description = "User password", example = "password123")
    @NotBlank
    private String password;
}
