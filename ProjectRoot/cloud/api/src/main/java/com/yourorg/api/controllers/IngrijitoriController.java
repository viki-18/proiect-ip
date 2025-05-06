package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.yourorg.api.model.Ingrijitor;
import com.yourorg.api.model.ExternalResponse;
import com.yourorg.api.service.IngrijitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<Ingrijitor> getIngrijitorById(@PathVariable Integer id) {
        Optional<Ingrijitor> ingrijitor = service.getIngrijitorById(id);
        return ingrijitor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ingrijitor createIngrijitor(@RequestBody Ingrijitor ingrijitor) {
        return service.createIngrijitor(ingrijitor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrijitor> updateIngrijitor(@PathVariable Integer id, @RequestBody Ingrijitor ingrijitor) {
        if (!service.getIngrijitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.updateIngrijitor(id, ingrijitor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngrijitor(@PathVariable Integer id) {
        if (!service.getIngrijitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteIngrijitor(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/send/{id}")
    public ResponseEntity<ExternalResponse> sendIngrijitorData(
            @PathVariable Integer id,
            @RequestParam String target) {
        
        Optional<Ingrijitor> ingrijitorOpt = service.getIngrijitorById(id);
        if (!ingrijitorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Ingrijitor ingrijitor = ingrijitorOpt.get();
        boolean success = service.sendToExternalSystem(ingrijitor, target);
        
        ExternalResponse response = new ExternalResponse(
            success ? "success" : "error",
            success ? "Data sent successfully to " + target : "Failed to send data",
            ingrijitor
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/batch/send")
    public ResponseEntity<ExternalResponse> sendMultipleIngrijitoriData(
            @RequestBody List<Integer> ingrijitorIds,
            @RequestParam String target) {
        
        List<Ingrijitor> sentIngrijitori = new ArrayList<>();
        List<Ingrijitor> failedIngrijitori = new ArrayList<>();
        
        for (Integer id : ingrijitorIds) {
            Optional<Ingrijitor> ingrijitorOpt = service.getIngrijitorById(id);
            if (ingrijitorOpt.isPresent()) {
                Ingrijitor ingrijitor = ingrijitorOpt.get();
                boolean success = service.sendToExternalSystem(ingrijitor, target);
                if (success) {
                    sentIngrijitori.add(ingrijitor);
                } else {
                    failedIngrijitori.add(ingrijitor);
                }
            }
        }
        
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("sent", sentIngrijitori);
        resultData.put("failed", failedIngrijitori);
        
        ExternalResponse response = new ExternalResponse(
            failedIngrijitori.isEmpty() ? "success" : "partial",
            sentIngrijitori.size() + " ingrijitori sent successfully, " + failedIngrijitori.size() + " failed",
            resultData
        );
        
        return ResponseEntity.ok(response);
    }
}