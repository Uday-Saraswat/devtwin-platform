package com.uday.authservice.service;

import com.uday.authservice.dto.LoginRequest;
import com.uday.authservice.dto.LoginResponse;
import com.uday.authservice.dto.RegisterRequest;
import com.uday.authservice.entity.User;

public interface AuthService {

    public User register(RegisterRequest request);

    public LoginResponse login(LoginRequest request);

}
