package com.luis.financial_backend.modules.auth.controller;


import com.luis.financial_backend.modules.auth.dto.AuthResponse;
import com.luis.financial_backend.modules.auth.dto.LoginRequest;
import com.luis.financial_backend.modules.auth.dto.RefreshTokenRequest;
import com.luis.financial_backend.modules.auth.dto.RegisterRequest;
import com.luis.financial_backend.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (
            @RequestBody @Valid RegisterRequest request
    ){

        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (
            @RequestBody @Valid LoginRequest request
    ){

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh (
            @RequestBody @Valid RefreshTokenRequest request
    ){

        AuthResponse response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout (
            @RequestBody @Valid RefreshTokenRequest request
    ){

        authService.logout(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me (
            @AuthenticationPrincipal(expression = "username") String username
    ){
        AuthResponse response = authService.me(username);
        return ResponseEntity.ok(response);
    }
}
