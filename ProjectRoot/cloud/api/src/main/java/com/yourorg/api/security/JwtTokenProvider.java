package com.yourorg.api.security;

import com.yourorg.api.model.Utilizator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Aceasta este o implementare minimală pentru a rezolva erorile de compilare.
// Logica reală pentru generarea și validarea JWT trebuie adăugată aici.

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret:your-256-bit-secret-key-here-make-it-long-and-secure}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // Metodă placeholder pentru generarea token-ului
    public String generateToken(Utilizator utilizator) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", utilizator.getId());
            claims.put("email", utilizator.getEmail());
            claims.put("role", utilizator.getTipUtilizator());

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSigningKey())
                    .compact();

            logger.info("Generated JWT token for user: {}", utilizator.getEmail());
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token: {}", e.getMessage());
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("email", String.class);
        } catch (Exception e) {
            logger.error("Error extracting email from token: {}", e.getMessage());
            throw new RuntimeException("Error extracting email from token", e);
        }
    }

    public String getUserRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (Exception e) {
            logger.error("Error extracting role from token: {}", e.getMessage());
            throw new RuntimeException("Error extracting role from token", e);
        }
    }
} 