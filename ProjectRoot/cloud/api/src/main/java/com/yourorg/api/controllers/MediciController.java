package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/medici")
public class MediciController {

    @GetMapping
    public List<String> getAllMedici() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getMedicById(@PathVariable int id) {
        return "Medic " + id;
    }

    @PostMapping
    public String createMedic(@RequestBody String medic) {
        return "Created medic";
    }

    @PutMapping("/{id}")
    public String updateMedic(@PathVariable int id, @RequestBody String medic) {
        return "Updated medic " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteMedic(@PathVariable int id) {
        return "Deleted medic " + id;
    }
}