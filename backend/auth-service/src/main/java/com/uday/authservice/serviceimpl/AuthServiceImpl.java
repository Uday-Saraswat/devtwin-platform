package com.uday.authservice.serviceimpl;

import com.uday.authservice.dto.RegisterRequest;
import com.uday.authservice.entity.User;
import com.uday.authservice.repository.UserRepository;
import com.uday.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder().name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role("USER").build();

        return userRepository.save(user);
    }
}
