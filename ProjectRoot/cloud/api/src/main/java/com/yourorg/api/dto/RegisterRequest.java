package com.yourorg.api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nrTelefon;
    private String tipUtilizator;
} 