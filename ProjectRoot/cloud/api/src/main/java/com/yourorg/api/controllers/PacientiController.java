package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/pacienti")
public class PacientiController {

    @GetMapping
    public List<String> getAllPacienti() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getPacientById(@PathVariable int id) {
        return "Pacient " + id;
    }

    @PostMapping
    public String createPacient(@RequestBody String pacient) {
        return "Created pacient";
    }

    @PutMapping("/{id}")
    public String updatePacient(@PathVariable int id, @RequestBody String pacient) {
        return "Updated pacient " + id;
    }

    @DeleteMapping("/{id}")
    public String deletePacient(@PathVariable int id) {
        return "Deleted pacient " + id;
    }
}