package com.luis.financial_backend.modules.auth.service;


import com.luis.financial_backend.common.exception.BadRequestException;
import com.luis.financial_backend.modules.auth.dto.*;
import com.luis.financial_backend.modules.auth.entity.Role;
import com.luis.financial_backend.modules.auth.entity.User;
import com.luis.financial_backend.modules.auth.mapper.AuthMapper;
import com.luis.financial_backend.modules.auth.repository.RoleRepository;
import com.luis.financial_backend.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    private User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    private User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }



    @Override
    public AuthResponse register(RegisterRequest request) {

        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();

        if(userRepository.existsByEmail(email)){
            throw new BadRequestException("The email already exists.");
        }

        if(userRepository.existsByUsername(username)){
            throw new BadRequestException("The name already exists.");
        }

        User user = authMapper.toUser(request);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new IllegalStateException("ROLE_USER not found"));

        Set<Role> roles = Optional.ofNullable(user.getRoles()).orElseGet(HashSet::new);
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return authMapper.toAuthResponse(user, accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = getUserByEmail(request.email());

        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return authMapper.toAuthResponse(user, accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();

        String email = jwtService.extractUsername(refreshToken);

        User user = getUserByEmail(email);

        boolean isValid = jwtService.isTokenValid(refreshToken, user.getEmail());
        if(!isValid){
            throw new BadRequestException("Invalid or expired refresh token.");
        }

        String accessToken = jwtService.generateToken(user.getEmail());
        String newRefreshToken = jwtService.generateRefreshToken(user.getEmail());

        return authMapper.toAuthResponse(user, accessToken, newRefreshToken);
    }


    @Override
    public AuthResponseMe me(String email) {

        User user = getUserByEmail(email);

        return authMapper.toAuthResponseMe(user);

    }
}
