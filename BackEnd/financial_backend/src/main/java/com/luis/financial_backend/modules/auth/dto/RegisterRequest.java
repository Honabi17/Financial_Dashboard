package com.luis.financial_backend.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        String username,

        @Email
        @NotBlank
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters.")
        @NotBlank
        String password
){}
