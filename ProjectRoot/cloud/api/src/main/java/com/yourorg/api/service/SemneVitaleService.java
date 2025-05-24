package com.yourorg.api.service;

import com.yourorg.api.model.SemneVitale;
import com.yourorg.api.repository.SemneVitaleRepository;
import com.yourorg.api.dto.AlarmaThresholdDTO;
import com.yourorg.api.dto.SemneVitaleValuesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SemneVitaleService {

    @Autowired
    private SemneVitaleRepository repository;

    public List<SemneVitale> getAllSemneVitaleByPacient(Integer idPacient) {
        return repository.findByIdPacient(idPacient);
    }

    public List<SemneVitale> getLatestSemneVitaleByPacient(Integer idPacient) {
        return repository.findByIdPacientOrderByDataInregistrareDesc(idPacient);
    }

    public Optional<SemneVitale> getLatestSemneVitale(Integer idPacient) {
        return repository.findFirstByIdPacientOrderByDataInregistrareDesc(idPacient);
    }

    @Transactional
    public SemneVitale createSemneVitale(Integer idPacient, SemneVitaleValuesDTO valuesDTO) {
        SemneVitale semneVitale = new SemneVitale();
        semneVitale.setIdPacient(idPacient);
        
        // Copy values from DTO
        semneVitale.setPuls(valuesDTO.getPuls());
        semneVitale.setTensiuneArteriala(valuesDTO.getTensiuneArteriala());
        semneVitale.setTemperaturaCorporala(valuesDTO.getTemperaturaCorporala());
        semneVitale.setGreutate(valuesDTO.getGreutate());
        semneVitale.setGlicemie(valuesDTO.getGlicemie());
        semneVitale.setLumina(valuesDTO.getLumina());
        semneVitale.setTemperaturaAmbientala(valuesDTO.getTemperaturaAmbientala());
        semneVitale.setGaz(valuesDTO.getGaz());
        semneVitale.setUmiditate(valuesDTO.getUmiditate());
        semneVitale.setProximitate(valuesDTO.getProximitate());
        
        // Copy alarm thresholds from latest record if available
        Optional<SemneVitale> latestRecord = getLatestSemneVitale(idPacient);
        if (latestRecord.isPresent()) {
            SemneVitale latest = latestRecord.get();
            semneVitale.setValAlarmaPuls(latest.getValAlarmaPuls());
            semneVitale.setValAlarmaPulsMin(latest.getValAlarmaPulsMin());
            semneVitale.setValAlarmaTensiune(latest.getValAlarmaTensiune());
            semneVitale.setValAlarmaTensiuneMin(latest.getValAlarmaTensiuneMin());
            semneVitale.setValAlarmaTemperatura(latest.getValAlarmaTemperatura());
            semneVitale.setValAlarmaTemperaturaMin(latest.getValAlarmaTemperaturaMin());
            semneVitale.setValAlarmaGreutate(latest.getValAlarmaGreutate());
            semneVitale.setValAlarmaGreutateMin(latest.getValAlarmaGreutateMin());
            semneVitale.setValAlarmaGlicemie(latest.getValAlarmaGlicemie());
            semneVitale.setValAlarmaGlicemieMin(latest.getValAlarmaGlicemieMin());
            semneVitale.setValAlarmaLumina(latest.getValAlarmaLumina());
            semneVitale.setValAlarmaTemperaturaAmb(latest.getValAlarmaTemperaturaAmb());
            semneVitale.setValAlarmaTemperaturaAmbMin(latest.getValAlarmaTemperaturaAmbMin());
            semneVitale.setValAlarmaGaz(latest.getValAlarmaGaz());
            semneVitale.setValAlarmaUmiditate(latest.getValAlarmaUmiditate());
            semneVitale.setValAlarmaProximitate(latest.getValAlarmaProximitate());
        }
        
        // Set timestamp
        semneVitale.setDataInregistrare(valuesDTO.getDataInregistrare() != null 
            ? valuesDTO.getDataInregistrare() 
            : LocalDateTime.now());
        
        return repository.save(semneVitale);
    }

    @Transactional
    public SemneVitale updateAlarmaThresholds(Integer idPacient, AlarmaThresholdDTO alarmaDTO) {
        // Get the latest record for the patient
        Optional<SemneVitale> latestRecordOpt = getLatestSemneVitale(idPacient);
        
        if (!latestRecordOpt.isPresent()) {
            // If no record exists, create a new one with just alarm thresholds
            SemneVitale newRecord = new SemneVitale();
            newRecord.setIdPacient(idPacient);
            newRecord.setDataInregistrare(LocalDateTime.now());
            applyAlarmaThresholds(newRecord, alarmaDTO);
            return repository.save(newRecord);
        }
        
        // Get existing record and update with new values
        SemneVitale existingRecord = latestRecordOpt.get();
        
        // Create a new record with the same vital signs but updated thresholds
        SemneVitale newRecord = new SemneVitale();
        newRecord.setIdPacient(idPacient);
        
        // Copy the current values
        newRecord.setPuls(existingRecord.getPuls());
        newRecord.setTensiuneArteriala(existingRecord.getTensiuneArteriala());
        newRecord.setTemperaturaCorporala(existingRecord.getTemperaturaCorporala());
        newRecord.setGreutate(existingRecord.getGreutate());
        newRecord.setGlicemie(existingRecord.getGlicemie());
        newRecord.setLumina(existingRecord.getLumina());
        newRecord.setTemperaturaAmbientala(existingRecord.getTemperaturaAmbientala());
        newRecord.setGaz(existingRecord.getGaz());
        newRecord.setUmiditate(existingRecord.getUmiditate());
        newRecord.setProximitate(existingRecord.getProximitate());
        
        // Apply the new alarm thresholds
        applyAlarmaThresholds(newRecord, alarmaDTO);
        
        // Set timestamp
        newRecord.setDataInregistrare(LocalDateTime.now());
        
        return repository.save(newRecord);
    }
    
    private void applyAlarmaThresholds(SemneVitale semneVitale, AlarmaThresholdDTO alarmaDTO) {
        // Update only the non-null values from the DTO
        if (alarmaDTO.getValAlarmaPuls() != null) {
            semneVitale.setValAlarmaPuls(alarmaDTO.getValAlarmaPuls());
        }
        
        if (alarmaDTO.getValAlarmaPulsMin() != null) {
            semneVitale.setValAlarmaPulsMin(alarmaDTO.getValAlarmaPulsMin());
        }
        
        if (alarmaDTO.getValAlarmaTensiune() != null) {
            semneVitale.setValAlarmaTensiune(alarmaDTO.getValAlarmaTensiune());
        }
        
        if (alarmaDTO.getValAlarmaTensiuneMin() != null) {
            semneVitale.setValAlarmaTensiuneMin(alarmaDTO.getValAlarmaTensiuneMin());
        }
        
        if (alarmaDTO.getValAlarmaTemperatura() != null) {
            semneVitale.setValAlarmaTemperatura(alarmaDTO.getValAlarmaTemperatura());
        }
        
        if (alarmaDTO.getValAlarmaTemperaturaMin() != null) {
            semneVitale.setValAlarmaTemperaturaMin(alarmaDTO.getValAlarmaTemperaturaMin());
        }
        
        if (alarmaDTO.getValAlarmaGreutate() != null) {
            semneVitale.setValAlarmaGreutate(alarmaDTO.getValAlarmaGreutate());
        }
        
        if (alarmaDTO.getValAlarmaGreutateMin() != null) {
            semneVitale.setValAlarmaGreutateMin(alarmaDTO.getValAlarmaGreutateMin());
        }
        
        if (alarmaDTO.getValAlarmaGlicemie() != null) {
            semneVitale.setValAlarmaGlicemie(alarmaDTO.getValAlarmaGlicemie());
        }
        
        if (alarmaDTO.getValAlarmaGlicemieMin() != null) {
            semneVitale.setValAlarmaGlicemieMin(alarmaDTO.getValAlarmaGlicemieMin());
        }
        
        if (alarmaDTO.getValAlarmaLumina() != null) {
            semneVitale.setValAlarmaLumina(alarmaDTO.getValAlarmaLumina());
        }
        
        if (alarmaDTO.getValAlarmaTemperaturaAmb() != null) {
            semneVitale.setValAlarmaTemperaturaAmb(alarmaDTO.getValAlarmaTemperaturaAmb());
        }
        
        if (alarmaDTO.getValAlarmaTemperaturaAmbMin() != null) {
            semneVitale.setValAlarmaTemperaturaAmbMin(alarmaDTO.getValAlarmaTemperaturaAmbMin());
        }
        
        if (alarmaDTO.getValAlarmaGaz() != null) {
            semneVitale.setValAlarmaGaz(alarmaDTO.getValAlarmaGaz());
        }
        
        if (alarmaDTO.getValAlarmaUmiditate() != null) {
            semneVitale.setValAlarmaUmiditate(alarmaDTO.getValAlarmaUmiditate());
        }
        
        if (alarmaDTO.getValAlarmaProximitate() != null) {
            semneVitale.setValAlarmaProximitate(alarmaDTO.getValAlarmaProximitate());
        }
    }
} 