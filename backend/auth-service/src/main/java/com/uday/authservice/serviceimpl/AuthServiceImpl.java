package com.uday.authservice.serviceimpl;

import com.uday.authservice.dto.LoginRequest;
import com.uday.authservice.dto.LoginResponse;
import com.uday.authservice.dto.RegisterRequest;
import com.uday.authservice.entity.User;
import com.uday.authservice.repository.UserRepository;
import com.uday.authservice.service.AuthService;
import com.uday.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder().name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role("USER").build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
        String token = jwtService.generateToken(request.getEmail());
        return LoginResponse.builder().token(token).build();
    }
}
