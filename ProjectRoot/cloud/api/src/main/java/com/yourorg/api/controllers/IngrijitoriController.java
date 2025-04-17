package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/ingrijitori")
public class IngrijitoriController {

    @GetMapping
    public List<String> getAllIngrijitori() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getIngrijitorById(@PathVariable int id) {
        return "Ingrijitor " + id;
    }

    @PostMapping
    public String createIngrijitor(@RequestBody String ingrijitor) {
        return "Created ingrijitor";
    }

    @PutMapping("/{id}")
    public String updateIngrijitor(@PathVariable int id, @RequestBody String ingrijitor) {
        return "Updated ingrijitor " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteIngrijitor(@PathVariable int id) {
        return "Deleted ingrijitor " + id;
    }
}