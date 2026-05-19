package com.uday.authservice.controller;

import com.uday.authservice.dto.RegisterRequest;
import com.uday.authservice.dto.RegisterResponse;
import com.uday.authservice.dto.UserResponse;
import com.uday.authservice.entity.User;
import com.uday.authservice.service.AuthService;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest registerRequest){

        User savedUser = authService.register(registerRequest);

        UserResponse response = UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();

        RegisterResponse<UserResponse> apiResponse =
                RegisterResponse.<UserResponse>builder()
                        .success(true)
                        .message("User Registered Successfully")
                        .data(response)
                        .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);

    }
}
