package com.bookMyShowClone.authService.service;

import com.bookMyShowClone.authService.dto.requestDto.LoginRequest;
import com.bookMyShowClone.authService.dto.requestDto.RegisterRequest;
import com.bookMyShowClone.authService.dto.responseDto.AuthResponse;
import com.bookMyShowClone.authService.dto.responseDto.UserResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    UserResponse register(RegisterRequest request) throws Throwable;
    AuthResponse login(LoginRequest request);
    UserResponse getCurrentUser(Authentication auth);
    AuthResponse refreshToken(String refreshToken);
}
