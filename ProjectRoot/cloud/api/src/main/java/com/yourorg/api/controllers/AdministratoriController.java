package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/administratori")
public class AdministratoriController {

    @GetMapping
    public List<String> getAllAdministratori() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getAdministratorById(@PathVariable int id) {
        return "Administrator " + id;
    }

    @PostMapping
    public String createAdministrator(@RequestBody String administrator) {
        return "Created administrator";
    }

    @PutMapping("/{id}")
    public String updateAdministrator(@PathVariable int id, @RequestBody String administrator) {
        return "Updated administrator " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteAdministrator(@PathVariable int id) {
        return "Deleted administrator " + id;
    }
}