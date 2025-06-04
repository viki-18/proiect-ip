package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.Utilizator;
import com.yourorg.api.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/utilizatori")
public class UtilizatoriController {

    @Autowired
    private UtilizatorService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Utilizator> getAllUtilizatori() {
        return service.getAllUtilizatori();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Integer id) {
        Optional<Utilizator> utilizator = service.getUtilizatorById(id);
        return utilizator.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUtilizator(@RequestBody Map<String, Object> payload) {
        try {
            // Extrage datele utilizatorului
            Utilizator utilizator = new Utilizator();
            utilizator.setEmail((String) payload.get("email"));
            utilizator.setNrTelefon((String) payload.get("nrTelefon"));
            utilizator.setTipUtilizator((String) payload.get("tipUtilizator"));
            // Hash parola înainte de a o salva
            String rawPassword = (String) payload.get("parola");
            utilizator.setParola(passwordEncoder.encode(rawPassword));
            utilizator.setNume((String) payload.get("nume"));
            utilizator.setPrenume((String) payload.get("prenume"));
            if (payload.get("varsta") != null) {
                utilizator.setVarsta(((Number) payload.get("varsta")).intValue());
            }
            utilizator.setGen((String) payload.get("gen"));

            // Extrage datele adiționale pentru tipul specific
            Map<String, Object> additionalData = new HashMap<>();
            if ("P".equals(utilizator.getTipUtilizator())) {
                additionalData.put("CNP", payload.get("CNP"));
                additionalData.put("localitate", payload.get("localitate"));
                additionalData.put("strada", payload.get("strada"));
                additionalData.put("nr", payload.get("nr"));
                additionalData.put("profesie", payload.get("profesie"));
                additionalData.put("idSmartphone", payload.get("idSmartphone"));
            }

            Object result = service.createUtilizatorWithType(utilizator, additionalData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilizator> updateUtilizator(@PathVariable Integer id, @RequestBody Utilizator utilizator) {
        try {
            Utilizator updated = service.updateUtilizator(id, utilizator);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable Integer id) {
        try {
            service.deleteUtilizator(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}