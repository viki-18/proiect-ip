package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/semne-vitale")
public class SemneVitaleController {

    @GetMapping
    public List<String> getAllSemneVitale() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getSemneVitaleById(@PathVariable int id) {
        return "Semne Vitale " + id;
    }

    @PostMapping
    public String createSemneVitale(@RequestBody String semneVitale) {
        return "Created semne vitale";
    }

    @PutMapping("/{id}")
    public String updateSemneVitale(@PathVariable int id, @RequestBody String semneVitale) {
        return "Updated semne vitale " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteSemneVitale(@PathVariable int id) {
        return "Deleted semne vitale " + id;
    }
}