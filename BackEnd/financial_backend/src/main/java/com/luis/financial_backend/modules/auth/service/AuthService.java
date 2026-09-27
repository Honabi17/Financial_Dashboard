package com.luis.financial_backend.modules.auth.service;


import com.luis.financial_backend.modules.auth.dto.*;


public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    AuthResponseMe me(String email);
}
