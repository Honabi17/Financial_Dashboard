package com.luis.financial_backend.modules.auth.dto;


import java.util.List;


public record AuthResponse(

        String accessToken,
        String refreshToken,
        String email,
        String username,
        List<RoleResponse> roles
){}
