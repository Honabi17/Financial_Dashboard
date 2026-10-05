package com.luis.financial_backend.modules.auth.dto;


import java.util.List;


public record AuthResponseMe(
        String username,
        String email,
        List<RoleResponse> roles
){}
