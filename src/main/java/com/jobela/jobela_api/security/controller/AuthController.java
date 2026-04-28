package com.jobela.jobela_api.security.controller;


import com.jobela.jobela_api.security.dto.AuthResponse;
import com.jobela.jobela_api.security.dto.CurrentUserResponse;
import com.jobela.jobela_api.security.dto.LoginRequest;
import com.jobela.jobela_api.security.dto.RegisterRequest;
import com.jobela.jobela_api.security.service.AuthService;
import com.jobela.jobela_api.security.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails) {

        var user = userDetails.getUser();

        return ResponseEntity.ok(
                new CurrentUserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
}
