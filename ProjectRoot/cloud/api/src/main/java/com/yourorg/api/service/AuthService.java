package com.yourorg.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(LoginRequest loginRequest) {
        logger.info("Attempting login for user: {}", loginRequest.getEmail());
        
        Utilizator utilizator = utilizatorRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> {
                logger.error("User not found: {}", loginRequest.getEmail());
                return new RuntimeException("Utilizatorul nu a fost găsit");
            });

        if (!passwordEncoder.matches(loginRequest.getPassword(), utilizator.getParola())) {
            logger.error("Invalid password for user: {}", loginRequest.getEmail());
            throw new RuntimeException("Parolă incorectă");
        }

        String tip = utilizator.getTipUtilizator();
        if (!("P".equals(tip) || "I".equals(tip) || "M".equals(tip)|| "A".equals(tip)|| "S".equals(tip))) {
            logger.error("Invalid user type for login: {}", tip);
            throw new RuntimeException("Doar pacienții și îngrijitorii pot folosi aplicația mobilă");
        }

        String token = jwtTokenProvider.generateToken(utilizator);
        logger.info("Successfully generated token for user: {}", loginRequest.getEmail());

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

        // Verifică dacă numărul de telefon există deja
        if (utilizatorRepository.findByNrTelefon(registerRequest.getNrTelefon()) != null) {
            throw new RuntimeException("Numărul de telefon este deja înregistrat");
        }

        // Creează utilizatorul nou
        Utilizator utilizator = new Utilizator();
        utilizator.setEmail(registerRequest.getEmail());
        utilizator.setParola(passwordEncoder.encode(registerRequest.getPassword()));
        utilizator.setNrTelefon(registerRequest.getNrTelefon());
        utilizator.setTipUtilizator(registerRequest.getTipUtilizator());

        // Salvează utilizatorul
        utilizatorRepository.save(utilizator);
    }

    public void forgotPassword(String email) {
        Utilizator utilizator = utilizatorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        // Aici poți adăuga logica pentru generarea și trimiterea link-ului de resetare
        // De exemplu, generarea unui token temporar și trimiterea unui email
    }
}
