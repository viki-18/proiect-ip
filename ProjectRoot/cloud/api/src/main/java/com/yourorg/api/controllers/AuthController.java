package com.yourorg.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yourorg.api.service.AuthService;
import com.yourorg.api.dto.LoginRequest;
import com.yourorg.api.dto.RegisterRequest;
import com.yourorg.api.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("Cererea de înregistrare a fost trimisă. Vei primi credențialele pe email.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody RegisterRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Link-ul de resetare a parolei a fost trimis pe email.");
    }
} 