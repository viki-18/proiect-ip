package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.Supraveghetor;
import com.yourorg.api.service.SupraveghetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/supraveghetori")
public class SupraveghetoriController {

    @Autowired
    private SupraveghetorService service;

    @GetMapping
    public List<Map<String, Object>> getAllSupraveghetori() {
        List<Supraveghetor> supraveghetori = service.getAllSupraveghetori();
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Supraveghetor supraveghetor : supraveghetori) {
            Map<String, Object> supraveghetorInfo = new HashMap<>();
            supraveghetorInfo.put("id", supraveghetor.getIdSupraveghetor());
            response.add(supraveghetorInfo);
        }
        
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSupraveghetorById(@PathVariable Integer id) {
        Optional<Supraveghetor> supraveghetor = service.getSupraveghetorById(id);
        if (supraveghetor.isPresent()) {
            Supraveghetor s = supraveghetor.get();
            Map<String, Object> supraveghetorInfo = new HashMap<>();
            supraveghetorInfo.put("id", s.getIdSupraveghetor());
            return ResponseEntity.ok(supraveghetorInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSupraveghetor(@RequestBody Supraveghetor supraveghetor) {
        Supraveghetor created = service.createSupraveghetor(supraveghetor);
        Map<String, Object> response = new HashMap<>();
        response.put("id", created.getIdSupraveghetor());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSupraveghetor(@PathVariable Integer id, @RequestBody Supraveghetor supraveghetor) {
        if (!service.getSupraveghetorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Supraveghetor updated = service.updateSupraveghetor(id, supraveghetor);
        Map<String, Object> response = new HashMap<>();
        response.put("id", updated.getIdSupraveghetor());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupraveghetor(@PathVariable Integer id) {
        if (!service.getSupraveghetorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteSupraveghetor(id);
        return ResponseEntity.ok().build();
    }
}