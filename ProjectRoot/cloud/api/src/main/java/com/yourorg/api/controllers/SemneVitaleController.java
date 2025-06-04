package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.yourorg.api.model.SemneVitale;
import com.yourorg.api.service.SemneVitaleService;
import com.yourorg.api.dto.AlarmaThresholdDTO;
import com.yourorg.api.dto.SemneVitaleValuesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/semne-vitale")
public class SemneVitaleController {

    @Autowired
    private SemneVitaleService service;

    @GetMapping("/pacient/{idPacient}")
    public ResponseEntity<SemneVitale> getSemneVitaleByPacient(@PathVariable Integer idPacient) {
        Optional<SemneVitale> latestRecord = service.getLatestSemneVitale(idPacient);
        return latestRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pacient/{idPacient}/latest")
    public ResponseEntity<SemneVitale> getLatestSemneVitale(@PathVariable Integer idPacient) {
        Optional<SemneVitale> semneVitale = service.getLatestSemneVitale(idPacient);
        return semneVitale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Add new vital signs for a patient
     */
    @PostMapping("/pacient/{idPacient}/values")
    public ResponseEntity<SemneVitale> addSemneVitale(
            @PathVariable Integer idPacient,
            @RequestBody SemneVitaleValuesDTO valuesDTO) {
        try {
            SemneVitale newRecord = service.createSemneVitale(idPacient, valuesDTO);
            return ResponseEntity.ok(newRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update alarm thresholds for a patient
     */
    @PostMapping("/pacient/{idPacient}/alarme")
    public ResponseEntity<SemneVitale> updateAlarmaThresholds(
            @PathVariable Integer idPacient,
            @RequestBody AlarmaThresholdDTO alarmaDTO) {
        try {
            SemneVitale updated = service.updateAlarmaThresholds(idPacient, alarmaDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}