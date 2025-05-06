package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.Pacient;
import com.yourorg.api.model.ExternalResponse;
import com.yourorg.api.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pacienti")
public class PacientiController {

    @Autowired
    private PacientService service;

    @GetMapping
    public List<Pacient> getAllPacienti() {
        return service.getAllPacienti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pacient> getPacientById(@PathVariable Integer id) {
        Optional<Pacient> pacient = service.getPacientById(id);
        return pacient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pacient createPacient(@RequestBody Pacient pacient) {
        return service.createPacient(pacient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pacient> updatePacient(@PathVariable Integer id, @RequestBody Pacient pacient) {
        if (!service.getPacientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.updatePacient(id, pacient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacient(@PathVariable Integer id) {
        if (!service.getPacientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deletePacient(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/send/{id}")
    public ResponseEntity<ExternalResponse> sendPacientData(
            @PathVariable Integer id,
            @RequestParam String target) {
        
        Optional<Pacient> pacientOpt = service.getPacientById(id);
        if (!pacientOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Pacient pacient = pacientOpt.get();
        boolean success = service.sendToExternalSystem(pacient, target);
        
        ExternalResponse response = new ExternalResponse(
            success ? "success" : "error",
            success ? "Data sent successfully to " + target : "Failed to send data",
            pacient
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/batch/send")
    public ResponseEntity<ExternalResponse> sendMultiplePacientiData(
            @RequestBody List<Integer> pacientIds,
            @RequestParam String target) {
        
        List<Pacient> sentPacienti = new ArrayList<>();
        List<Pacient> failedPacienti = new ArrayList<>();
        
        for (Integer id : pacientIds) {
            Optional<Pacient> pacientOpt = service.getPacientById(id);
            if (pacientOpt.isPresent()) {
                Pacient pacient = pacientOpt.get();
                boolean success = service.sendToExternalSystem(pacient, target);
                if (success) {
                    sentPacienti.add(pacient);
                } else {
                    failedPacienti.add(pacient);
                }
            }
        }
        
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("sent", sentPacienti);
        resultData.put("failed", failedPacienti);
        
        ExternalResponse response = new ExternalResponse(
            failedPacienti.isEmpty() ? "success" : "partial",
            sentPacienti.size() + " pacienti sent successfully, " + failedPacienti.size() + " failed",
            resultData
        );
        
        return ResponseEntity.ok(response);
    }
}