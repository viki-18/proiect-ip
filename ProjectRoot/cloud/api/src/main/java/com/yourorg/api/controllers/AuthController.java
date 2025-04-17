package com.yourorg.api.controllers;

import com.yourorg.api.model.Utilizator;
import com.yourorg.api.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilizatorService utilizatorService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String role) {
        Optional<Utilizator> user = utilizatorService.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getRole().equals(role))
                .findFirst();

        return user.isPresent() ? "Login successful" : "Invalid credentials";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Utilizator newUser) {
        utilizatorService.save(newUser);
        return "User registered successfully";
    }
}