package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/utilizatori")
public class UtilizatoriController {

    @GetMapping
    public List<String> getAllUtilizatori() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getUtilizatorById(@PathVariable int id) {
        return "Utilizator " + id;
    }

    @PostMapping
    public String createUtilizator(@RequestBody String utilizator) {
        return "Created utilizator";
    }

    @PutMapping("/{id}")
    public String updateUtilizator(@PathVariable int id, @RequestBody String utilizator) {
        return "Updated utilizator " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteUtilizator(@PathVariable int id) {
        return "Deleted utilizator " + id;
    }
}