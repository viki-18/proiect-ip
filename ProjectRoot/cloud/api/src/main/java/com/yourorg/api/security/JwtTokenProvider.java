package com.yourorg.api.security;

import com.yourorg.api.model.Utilizator;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Aceasta este o implementare minimală pentru a rezolva erorile de compilare.
// Logica reală pentru generarea și validarea JWT trebuie adăugată aici.

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:defaultSecretKey123456789012345678901234567890}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long jwtExpiration;

    // Metodă placeholder pentru generarea token-ului
    public String generateToken(Utilizator utilizator) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", utilizator.getId());
        claims.put("email", utilizator.getEmail());
        claims.put("role", utilizator.getTipUtilizator());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }

    public String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }
} 