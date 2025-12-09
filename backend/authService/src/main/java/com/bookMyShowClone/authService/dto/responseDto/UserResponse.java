package com.bookMyShowClone.authService.dto.responseDto;

import java.util.List;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String email;
    private List<String> roles;
}
