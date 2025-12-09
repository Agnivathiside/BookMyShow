package com.bookMyShowClone.authService.service.impl;

import com.bookMyShowClone.authService.dto.requestDto.LoginRequest;
import com.bookMyShowClone.authService.dto.requestDto.RegisterRequest;
import com.bookMyShowClone.authService.dto.responseDto.AuthResponse;
import com.bookMyShowClone.authService.dto.responseDto.UserResponse;
import com.bookMyShowClone.authService.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public UserResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public UserResponse getCurrentUser(Authentication auth) {
        return null;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        return null;
    }
}
