package com.luis.financial_backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshToken(

        @NotBlank
        String refreshToken
){}
