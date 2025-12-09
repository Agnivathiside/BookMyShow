package com.bookMyShowClone.authService.service.impl;

import com.bookMyShowClone.authService.dto.requestDto.LoginRequest;
import com.bookMyShowClone.authService.dto.requestDto.RegisterRequest;
import com.bookMyShowClone.authService.dto.responseDto.AuthResponse;
import com.bookMyShowClone.authService.dto.responseDto.UserResponse;
import com.bookMyShowClone.authService.entity.User;
import com.bookMyShowClone.authService.exception.UserAlreadyExistsException;
import com.bookMyShowClone.authService.repository.UserRepository;
import com.bookMyShowClone.authService.security.JwtUtil;
import com.bookMyShowClone.authService.service.AuthService;
import com.bookMyShowClone.authService.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(RegisterRequest request) throws Throwable {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Throwable("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of("USER"));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getRoles()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // authenticate user email/password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).get();

        try {
            String userId = user.getId().toString();

            String accessToken = jwtUtil.generateAccessToken(
                    userId,
                    user.getEmail(),
                    user.getRoles()
            );

            String refreshToken = jwtUtil.generateRefreshToken(userId);

            // 🔥 FIX: use SHA-256 instead of BCrypt for hashing refresh token
            String refreshTokenHash = HashUtil.sha256(refreshToken);

            user.setRefreshTokenHash(refreshTokenHash);
            user.setUpdatedAt(Instant.now());
            userRepository.save(user);

            return new AuthResponse(accessToken, refreshToken);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token generation failed: " + e.getMessage());
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            String userId = jwtUtil.extractUserId(refreshToken);
            UUID uuid = UUID.fromString(userId);

            Optional<User> userOpt = userRepository.findById(uuid);
            if (userOpt.isEmpty()) {
                throw new RuntimeException("Invalid refresh token");
            }

            User user = userOpt.get();

            // 🔥 FIX: Compare SHA-256 refresh token hashes
            String incomingHash = HashUtil.sha256(refreshToken);
            if (!incomingHash.equals(user.getRefreshTokenHash())) {
                throw new RuntimeException("Invalid refresh token");
            }

            String newAccessToken = jwtUtil.generateAccessToken(
                    userId,
                    user.getEmail(),
                    user.getRoles()
            );

            return new AuthResponse(newAccessToken, refreshToken);

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token: " + e.getMessage());
        }
    }

    @Override
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );
    }
}
