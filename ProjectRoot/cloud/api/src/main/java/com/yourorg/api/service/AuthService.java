package com.yourorg.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.yourorg.api.dto.LoginRequest;
import com.yourorg.api.dto.RegisterRequest;
import com.yourorg.api.dto.AuthResponse;
import com.yourorg.api.model.Utilizator;
import com.yourorg.api.repository.UtilizatorRepository;
import com.yourorg.api.security.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(LoginRequest loginRequest) {
        Utilizator utilizator = utilizatorRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        // Verifică parola ca text simplu
        if (!loginRequest.getPassword().equals(utilizator.getParola())) {
            throw new RuntimeException("Parolă incorectă");
        }

        // Permite doar pentru pacient sau ingrijitor
        String tip = utilizator.getTipUtilizator();
        if (!("P".equals(tip) || "I".equals(tip))) {
            throw new RuntimeException("Doar pacienții și îngrijitorii pot folosi aplicația mobilă");
        }

        String token = jwtTokenProvider.generateToken(utilizator);

        return AuthResponse.builder()
            .token(token)
            .user(AuthResponse.UserDto.builder()
                .id(utilizator.getId().longValue())
                .email(utilizator.getEmail())
                .role(utilizator.getTipUtilizator())
                .build())
            .build();
    }

    public void register(RegisterRequest registerRequest) {
        // Verifică dacă email-ul există deja
        if (utilizatorRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email-ul este deja înregistrat");
        }

        // Aici poți adăuga logica pentru trimiterea email-ului către administrator
        // și crearea contului temporar sau marcarea pentru aprobare
    }

    public void forgotPassword(String email) {
        Utilizator utilizator = utilizatorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        // Aici poți adăuga logica pentru generarea și trimiterea link-ului de resetare
        // De exemplu, generarea unui token temporar și trimiterea unui email
    }
}
