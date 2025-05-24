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
    public List<Map<String, Object>> getAllPacienti() {
        List<Pacient> pacienti = service.getAllPacienti();
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Pacient pacient : pacienti) {
            Map<String, Object> pacientInfo = new HashMap<>();
            pacientInfo.put("id", pacient.getIdPacient());
            pacientInfo.put("utilizatorId", pacient.getUtilizatorId());
            pacientInfo.put("localitate", pacient.getLocalitate());
            pacientInfo.put("strada", pacient.getStrada());
            pacientInfo.put("nr", pacient.getNr());
            pacientInfo.put("CNP", pacient.getCNP());
            pacientInfo.put("profesie", pacient.getProfesie());
            pacientInfo.put("idMedic", pacient.getIdMedic());
            pacientInfo.put("recomandariDeLaMedic", pacient.getRecomandariDeLaMedic());
            pacientInfo.put("idSupraveghetor", pacient.getIdSupraveghetor());
            pacientInfo.put("idIngrijitor", pacient.getIdIngrijitor());
            pacientInfo.put("idSmartphone", pacient.getIdSmartphone());
            pacientInfo.put("pathRapoarte", pacient.getPathRapoarte());
            response.add(pacientInfo);
        }
        
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPacientById(@PathVariable Integer id) {
        Optional<Pacient> pacient = service.getPacientById(id);
        if (pacient.isPresent()) {
            Pacient p = pacient.get();
            Map<String, Object> pacientInfo = new HashMap<>();
            pacientInfo.put("id", p.getIdPacient());
            pacientInfo.put("utilizatorId", p.getUtilizatorId());
            pacientInfo.put("localitate", p.getLocalitate());
            pacientInfo.put("strada", p.getStrada());
            pacientInfo.put("nr", p.getNr());
            pacientInfo.put("CNP", p.getCNP());
            pacientInfo.put("profesie", p.getProfesie());
            pacientInfo.put("idMedic", p.getIdMedic());
            pacientInfo.put("recomandariDeLaMedic", p.getRecomandariDeLaMedic());
            pacientInfo.put("idSupraveghetor", p.getIdSupraveghetor());
            pacientInfo.put("idIngrijitor", p.getIdIngrijitor());
            pacientInfo.put("idSmartphone", p.getIdSmartphone());
            pacientInfo.put("pathRapoarte", p.getPathRapoarte());
            return ResponseEntity.ok(pacientInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPacient(@RequestBody Pacient pacient) {
        Pacient created = service.createPacient(pacient);
        Map<String, Object> response = new HashMap<>();
        response.put("id", created.getIdPacient());
        response.put("utilizatorId", created.getUtilizatorId());
        response.put("localitate", created.getLocalitate());
        response.put("strada", created.getStrada());
        response.put("nr", created.getNr());
        response.put("CNP", created.getCNP());
        response.put("profesie", created.getProfesie());
        response.put("idMedic", created.getIdMedic());
        response.put("recomandariDeLaMedic", created.getRecomandariDeLaMedic());
        response.put("idSupraveghetor", created.getIdSupraveghetor());
        response.put("idIngrijitor", created.getIdIngrijitor());
        response.put("idSmartphone", created.getIdSmartphone());
        response.put("pathRapoarte", created.getPathRapoarte());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePacient(@PathVariable Integer id, @RequestBody Pacient pacient) {
        if (!service.getPacientById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Pacient updated = service.updatePacient(id, pacient);
        Map<String, Object> response = new HashMap<>();
        response.put("id", updated.getIdPacient());
        response.put("utilizatorId", updated.getUtilizatorId());
        response.put("localitate", updated.getLocalitate());
        response.put("strada", updated.getStrada());
        response.put("nr", updated.getNr());
        response.put("CNP", updated.getCNP());
        response.put("profesie", updated.getProfesie());
        response.put("idMedic", updated.getIdMedic());
        response.put("recomandariDeLaMedic", updated.getRecomandariDeLaMedic());
        response.put("idSupraveghetor", updated.getIdSupraveghetor());
        response.put("idIngrijitor", updated.getIdIngrijitor());
        response.put("idSmartphone", updated.getIdSmartphone());
        response.put("pathRapoarte", updated.getPathRapoarte());
        return ResponseEntity.ok(response);
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