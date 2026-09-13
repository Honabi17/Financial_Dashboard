package com.luis.financial_backend.modules.auth.mapper;


import com.luis.financial_backend.modules.auth.dto.AuthResponse;
import com.luis.financial_backend.modules.auth.dto.PermissionResponse;
import com.luis.financial_backend.modules.auth.dto.RegisterRequest;
import com.luis.financial_backend.modules.auth.dto.RoleResponse;
import com.luis.financial_backend.modules.auth.entity.Permission;
import com.luis.financial_backend.modules.auth.entity.Role;
import com.luis.financial_backend.modules.auth.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
public class AuthMapper{

    public User toUser(RegisterRequest request){
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public AuthResponse toAuthResponse(
            User user, String accessToken, String refreshToken
    ){
        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getUsername(),
                user.getEmail(),
                toRoleResponse(user.getRoles())
        );
    }

    public List<RoleResponse> toRoleResponse(Set<Role> roles){
        return roles.stream()
                .map(role -> new RoleResponse(
                        role.getName(),
                        role.getPermissions()
                                .stream()
                                .map(
                                        permission -> new PermissionResponse(permission.getName())
                                )
                                .toList()
                )).toList();
    }
}
