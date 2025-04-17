package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/alarme")
public class AlarmeController {

    @GetMapping
    public List<String> getAllAlarme() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getAlarmaById(@PathVariable int id) {
        return "Alarma " + id;
    }

    @PostMapping
    public String createAlarma(@RequestBody String alarma) {
        return "Created alarma";
    }

    @PutMapping("/{id}")
    public String updateAlarma(@PathVariable int id, @RequestBody String alarma) {
        return "Updated alarma " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteAlarma(@PathVariable int id) {
        return "Deleted alarma " + id;
    }
}