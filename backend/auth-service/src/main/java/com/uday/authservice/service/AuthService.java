package com.uday.authservice.service;

import com.uday.authservice.dto.RegisterRequest;
import com.uday.authservice.entity.User;

public interface AuthService {

    public User register(RegisterRequest request);

}
