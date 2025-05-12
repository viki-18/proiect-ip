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
    public List<SemneVitale> getSemneVitaleByPacient(@PathVariable Integer idPacient) {
        return service.getAllSemneVitaleByPacient(idPacient);
    }

    @GetMapping("/pacient/{idPacient}/latest")
    public ResponseEntity<SemneVitale> getLatestSemneVitale(@PathVariable Integer idPacient) {
        Optional<SemneVitale> semneVitale = service.getLatestSemneVitale(idPacient);
        return semneVitale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/pacient/{idPacient}")
    public SemneVitale addSemneVitale(
            @PathVariable Integer idPacient,
            @RequestBody SemneVitaleValuesDTO valuesDTO) {
        return service.createSemneVitale(idPacient, valuesDTO);
    }

    /**
     * Update the alarm thresholds for a patient
     * @param idPacient the patient ID
     * @param alarmaDTO the new alarm threshold values
     * @return the updated semne vitale record
     */
    @PutMapping("/pacient/{idPacient}/alarme")
    public SemneVitale updateAlarmaThresholds(
            @PathVariable Integer idPacient,
            @RequestBody AlarmaThresholdDTO alarmaDTO) {
        return service.updateAlarmaThresholds(idPacient, alarmaDTO);
    }
    
    /**
     * Create a new vital signs record with values and optional alarm thresholds
     * @param idPacient the patient ID
     * @param valuesDTO the vital signs values
     * @param updateAlarme whether to update alarm thresholds as well
     * @param alarmaDTO the alarm threshold values (optional)
     * @return the created semne vitale record
     */
    @PostMapping("/pacient/{idPacient}/complete")
    public SemneVitale addCompleteRecord(
            @PathVariable Integer idPacient,
            @RequestBody SemneVitaleValuesDTO valuesDTO,
            @RequestParam(required = false, defaultValue = "false") boolean updateAlarme,
            @RequestParam(required = false) AlarmaThresholdDTO alarmaDTO) {
        
        // First create the record with the vital signs values
        SemneVitale newRecord = service.createSemneVitale(idPacient, valuesDTO);
        
        // If requested, also update alarm thresholds
        if (updateAlarme && alarmaDTO != null) {
            newRecord = service.updateAlarmaThresholds(idPacient, alarmaDTO);
        }
        
        return newRecord;
    }
}