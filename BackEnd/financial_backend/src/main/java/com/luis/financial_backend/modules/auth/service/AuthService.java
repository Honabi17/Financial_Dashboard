package com.luis.financial_backend.modules.auth.service;


import com.luis.financial_backend.modules.auth.dto.AuthResponse;
import com.luis.financial_backend.modules.auth.dto.LoginRequest;
import com.luis.financial_backend.modules.auth.dto.RefreshTokenRequest;
import com.luis.financial_backend.modules.auth.dto.RegisterRequest;


public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    void logout(RefreshTokenRequest request);
    AuthResponse me(String username);
}
