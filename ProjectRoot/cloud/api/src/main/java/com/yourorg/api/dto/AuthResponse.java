package com.yourorg.api.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AuthResponse {
    private String token;
    private UserDto user;

    @Data
    @Builder
    public static class UserDto {
        private Long id;
        private String email;
        private String role;
    }
} 