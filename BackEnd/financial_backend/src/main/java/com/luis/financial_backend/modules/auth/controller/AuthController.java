package com.luis.financial_backend.modules.auth.controller;


import com.luis.financial_backend.modules.auth.dto.*;
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

    @GetMapping("/me")
    public ResponseEntity<AuthResponseMe> me (
            @AuthenticationPrincipal(expression = "username") String email
    ){
        AuthResponseMe response = authService.me(email);
        return ResponseEntity.ok(response);
    }
}
