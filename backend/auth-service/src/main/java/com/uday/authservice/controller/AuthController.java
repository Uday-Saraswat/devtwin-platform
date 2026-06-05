package com.uday.authservice.controller;

import com.uday.authservice.dto.*;
import com.uday.authservice.entity.User;
import com.uday.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs", description = "User Registration and Login APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User Registration",
            description = "Registers a new user in the system")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User Registered Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {

        User savedUser = authService.register(registerRequest);

        UserResponse response = UserResponse.builder().id(savedUser.getId()).name(savedUser.getName()).email(savedUser.getEmail()).role(savedUser.getRole()).build();

        RegisterResponse<UserResponse> apiResponse = RegisterResponse.<UserResponse>builder().success(true).message("User Registered Successfully").data(response).build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @Operation(summary = "User Login",
            description = "Authenticates user and returns JWT token")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login Successful"),
            @ApiResponse(responseCode = "401", description = "Invalid Credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/login")
    public ResponseEntity<RegisterResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse loginResponse = authService.login(request);

        RegisterResponse<LoginResponse> apiResponse = RegisterResponse.<LoginResponse>builder().success(true).message("Login Successful").data(loginResponse).build();

        return ResponseEntity.ok(apiResponse);
    }
}
