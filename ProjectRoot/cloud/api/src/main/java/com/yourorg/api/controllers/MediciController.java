package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.Medic;
import com.yourorg.api.model.ExternalResponse;
import com.yourorg.api.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/medici")
public class MediciController {

    @Autowired
    private MedicService service;

    @GetMapping
    public List<Medic> getAllMedici() {
        return service.getAllMedici();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medic> getMedicById(@PathVariable Integer id) {
        Optional<Medic> medic = service.getMedicById(id);
        return medic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medic createMedic(@RequestBody Medic medic) {
        return service.createMedic(medic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medic> updateMedic(@PathVariable Integer id, @RequestBody Medic medic) {
        if (!service.getMedicById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.updateMedic(id, medic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedic(@PathVariable Integer id) {
        if (!service.getMedicById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteMedic(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/send/{id}")
    public ResponseEntity<ExternalResponse> sendMedicData(
            @PathVariable Integer id,
            @RequestParam String target) {
        
        Optional<Medic> medicOpt = service.getMedicById(id);
        if (!medicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Medic medic = medicOpt.get();
        boolean success = service.sendToExternalSystem(medic, target);
        
        ExternalResponse response = new ExternalResponse(
            success ? "success" : "error",
            success ? "Data sent successfully to " + target : "Failed to send data",
            medic
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/batch/send")
    public ResponseEntity<ExternalResponse> sendMultipleMediciData(
            @RequestBody List<Integer> medicIds,
            @RequestParam String target) {
        
        List<Medic> sentMedici = new ArrayList<>();
        List<Medic> failedMedici = new ArrayList<>();
        
        for (Integer id : medicIds) {
            Optional<Medic> medicOpt = service.getMedicById(id);
            if (medicOpt.isPresent()) {
                Medic medic = medicOpt.get();
                boolean success = service.sendToExternalSystem(medic, target);
                if (success) {
                    sentMedici.add(medic);
                } else {
                    failedMedici.add(medic);
                }
            }
        }
        
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("sent", sentMedici);
        resultData.put("failed", failedMedici);
        
        ExternalResponse response = new ExternalResponse(
            failedMedici.isEmpty() ? "success" : "partial",
            sentMedici.size() + " medici sent successfully, " + failedMedici.size() + " failed",
            resultData
        );
        
        return ResponseEntity.ok(response);
    }
}