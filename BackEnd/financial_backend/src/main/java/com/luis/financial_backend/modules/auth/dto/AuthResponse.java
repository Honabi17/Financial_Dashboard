package com.luis.financial_backend.modules.auth.dto;

import com.luis.financial_backend.modules.auth.entity.Role;

import java.util.Set;

public record AuthResponse(

        String accessToken,
        String refreshToken,
        String email,
        String username,
        Set<Role> roles
){}
