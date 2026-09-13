package com.luis.financial_backend.modules.auth.dto;


import java.util.List;


public record RoleResponse(
        String name,
        List<PermissionResponse> permissions
){}
