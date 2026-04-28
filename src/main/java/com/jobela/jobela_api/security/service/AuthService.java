package com.jobela.jobela_api.security.service;

import com.jobela.jobela_api.common.exception.UserAlreadyExistsException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.security.dto.AuthResponse;
import com.jobela.jobela_api.security.dto.LoginRequest;
import com.jobela.jobela_api.security.dto.RegisterRequest;
import com.jobela.jobela_api.security.jwt.JwtService;
import com.jobela.jobela_api.security.user.CustomUserDetails;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StringMapperHelper stringMapperHelper;

    public AuthResponse register(RegisterRequest request) {
        var cleanedEmail = stringMapperHelper.clean(request.email());

        log.info("Registering user with email={}", cleanedEmail);

        if (userRepository.existsByEmailIgnoreCase(cleanedEmail)) {
            throw new UserAlreadyExistsException("User with email already exists: " + cleanedEmail);
        }

        var user = User.builder()
                .email(cleanedEmail)
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .build();

        var savedUser = userRepository.save(user);

        var token = jwtService.generateToken(new CustomUserDetails(savedUser));

        log.info("User registered successfully with id={}", savedUser.getId());

        return new AuthResponse(token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        var cleanedEmail = stringMapperHelper.clean(request.email());

        log.info("Login attempt for email={}", cleanedEmail);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(cleanedEmail, request.password())
        );

        var user = userRepository.findByEmailIgnoreCase(cleanedEmail).orElseThrow();

        var token = jwtService.generateToken(new CustomUserDetails(user));

        log.info("Login successful for user id={}", user.getId());

        return new AuthResponse(token);
    }
}
