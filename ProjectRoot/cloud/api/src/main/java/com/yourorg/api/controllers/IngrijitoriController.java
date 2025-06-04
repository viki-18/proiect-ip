package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.Ingrijitor;
import com.yourorg.api.service.IngrijitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/ingrijitori")
public class IngrijitoriController {

    @Autowired
    private IngrijitorService service;

    @GetMapping
    public List<Map<String, Object>> getAllIngrijitori() {
        List<Ingrijitor> ingrijitori = service.getAllIngrijitori();
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Ingrijitor ingrijitor : ingrijitori) {
            Map<String, Object> ingrijitorInfo = new HashMap<>();
            ingrijitorInfo.put("id", ingrijitor.getIdIngrijitor());
            ingrijitorInfo.put("nume", ingrijitor.getNume());
            ingrijitorInfo.put("prenume", ingrijitor.getPrenume());
            ingrijitorInfo.put("varsta", ingrijitor.getVarsta());
            ingrijitorInfo.put("gen", ingrijitor.getGen());
            ingrijitorInfo.put("specializare", ingrijitor.getSpecializare());
            response.add(ingrijitorInfo);
        }
        
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getIngrijitorById(@PathVariable Integer id) {
        Optional<Ingrijitor> ingrijitor = service.getIngrijitorById(id);
        if (ingrijitor.isPresent()) {
            Ingrijitor i = ingrijitor.get();
            Map<String, Object> ingrijitorInfo = new HashMap<>();
            ingrijitorInfo.put("id", i.getIdIngrijitor());
            ingrijitorInfo.put("nume", i.getNume());
            ingrijitorInfo.put("prenume", i.getPrenume());
            ingrijitorInfo.put("varsta", i.getVarsta());
            ingrijitorInfo.put("gen", i.getGen());
            ingrijitorInfo.put("specializare", i.getSpecializare());
            return ResponseEntity.ok(ingrijitorInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createIngrijitor(@RequestBody Ingrijitor ingrijitor) {
        Ingrijitor created = service.createIngrijitor(ingrijitor);
        Map<String, Object> response = new HashMap<>();
        response.put("id", created.getIdIngrijitor());
        response.put("nume", created.getNume());
        response.put("prenume", created.getPrenume());
        response.put("varsta", created.getVarsta());
        response.put("gen", created.getGen());
        response.put("specializare", created.getSpecializare());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateIngrijitor(@PathVariable Integer id, @RequestBody Ingrijitor ingrijitor) {
        if (!service.getIngrijitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Ingrijitor updated = service.updateIngrijitor(id, ingrijitor);
        Map<String, Object> response = new HashMap<>();
        response.put("id", updated.getIdIngrijitor());
        response.put("nume", updated.getNume());
        response.put("prenume", updated.getPrenume());
        response.put("varsta", updated.getVarsta());
        response.put("gen", updated.getGen());
        response.put("specializare", updated.getSpecializare());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngrijitor(@PathVariable Integer id) {
        if (!service.getIngrijitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteIngrijitor(id);
        return ResponseEntity.ok().build();
    }
}