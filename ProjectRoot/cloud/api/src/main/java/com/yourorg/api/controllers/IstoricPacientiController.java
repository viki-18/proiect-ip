package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/istoric-pacienti")
public class IstoricPacientiController {

    @GetMapping
    public List<String> getAllIstoricPacienti() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getIstoricPacientById(@PathVariable int id) {
        return "Istoric Pacient " + id;
    }

    @PostMapping
    public String createIstoricPacient(@RequestBody String istoricPacient) {
        return "Created istoric pacient";
    }

    @PutMapping("/{id}")
    public String updateIstoricPacient(@PathVariable int id, @RequestBody String istoricPacient) {
        return "Updated istoric pacient " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteIstoricPacient(@PathVariable int id) {
        return "Deleted istoric pacient " + id;
    }
}