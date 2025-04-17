package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/supraveghetori")
public class SupraveghetoriController {

    @GetMapping
    public List<String> getAllSupraveghetori() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public String getSupraveghetorById(@PathVariable int id) {
        return "Supraveghetor " + id;
    }

    @PostMapping
    public String createSupraveghetor(@RequestBody String supraveghetor) {
        return "Created supraveghetor";
    }

    @PutMapping("/{id}")
    public String updateSupraveghetor(@PathVariable int id, @RequestBody String supraveghetor) {
        return "Updated supraveghetor " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteSupraveghetor(@PathVariable int id) {
        return "Deleted supraveghetor " + id;
    }
}