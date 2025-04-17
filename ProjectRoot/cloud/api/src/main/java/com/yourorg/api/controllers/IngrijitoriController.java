package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;
import com.yourorg.api.model.Ingrijitor;
import com.yourorg.api.service.IngrijitorService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@RestController
@RequestMapping("/ingrijitori")
public class IngrijitoriController {

    @Autowired
    private IngrijitorService service;

    @GetMapping
    public List<Ingrijitor> getAllIngrijitori() {
        return service.getAllIngrijitori();
    }

    @GetMapping("/{id}")
    public Optional<Ingrijitor> getIngrijitorById(@PathVariable Long id) {
        return service.getIngrijitorById(id);
    }

    @PostMapping
    public Ingrijitor createIngrijitor(@RequestBody Ingrijitor ingrijitor) {
        return service.createIngrijitor(ingrijitor);
    }

    @PutMapping("/{id}")
    public Ingrijitor updateIngrijitor(@PathVariable Long id, @RequestBody Ingrijitor ingrijitor) {
        return service.updateIngrijitor(id, ingrijitor);
    }

    @DeleteMapping("/{id}")
    public void deleteIngrijitor(@PathVariable Long id) {
        service.deleteIngrijitor(id);
    }
}