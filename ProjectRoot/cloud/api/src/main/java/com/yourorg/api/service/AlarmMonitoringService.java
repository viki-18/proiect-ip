package com.yourorg.api.service;

import com.yourorg.api.model.SemneVitale;
import com.yourorg.api.repository.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmMonitoringService {
    
    private static final Logger logger = LoggerFactory.getLogger(AlarmMonitoringService.class);
    
    @Autowired
    private SemneVitaleService semneVitaleService;
    
    @Autowired
    private AlarmEventService alarmEventService;
    
    @Autowired
    private PacientRepository pacientRepository;
    
    /**
     * Checks every minute if any patient's vital signs exceed their alarm thresholds
     */
    @Scheduled(fixedRate = 60000) // Run every 60 seconds (1 minute)
    public void checkAlarms() {
        logger.info("Running scheduled alarm check");
        
        // Get all patient IDs
        List<Integer> patientIds = pacientRepository.findAllIds();
        
        // Check alarms for each patient
        for (Integer patientId : patientIds) {
            checkPatientAlarms(patientId);
        }
    }
    
    private void checkPatientAlarms(Integer patientId) {
        Optional<SemneVitale> latestVitals = semneVitaleService.getLatestSemneVitale(patientId);
        
        if (!latestVitals.isPresent()) {
            logger.info("No vital signs data available for patient {}", patientId);
            return;
        }
        
        SemneVitale vitals = latestVitals.get();
        
        // Check heart rate
        checkMaxThreshold(patientId, vitals.getPuls(), vitals.getValAlarmaPuls(), "Heart Rate", "BPM");
        checkMinThreshold(patientId, vitals.getPuls(), vitals.getValAlarmaPulsMin(), "Heart Rate", "BPM");
        
        // Check blood pressure
        checkMaxThreshold(patientId, vitals.getTensiuneArteriala(), vitals.getValAlarmaTensiune(), "Blood Pressure", "mmHg");
        checkMinThreshold(patientId, vitals.getTensiuneArteriala(), vitals.getValAlarmaTensiuneMin(), "Blood Pressure", "mmHg");
        
        // Check temperature
        checkMaxThreshold(patientId, vitals.getTemperaturaCorporala(), vitals.getValAlarmaTemperatura(), "Body Temperature", "°C");
        checkMinThreshold(patientId, vitals.getTemperaturaCorporala(), vitals.getValAlarmaTemperaturaMin(), "Body Temperature", "°C");
        
        // Check weight
        checkMaxThreshold(patientId, vitals.getGreutate(), vitals.getValAlarmaGreutate(), "Weight", "kg");
        checkMinThreshold(patientId, vitals.getGreutate(), vitals.getValAlarmaGreutateMin(), "Weight", "kg");
        
        // Check glucose
        checkMaxThreshold(patientId, vitals.getGlicemie(), vitals.getValAlarmaGlicemie(), "Glucose Level", "mg/dL");
        checkMinThreshold(patientId, vitals.getGlicemie(), vitals.getValAlarmaGlicemieMin(), "Glucose Level", "mg/dL");
        
        // Check ambient temperature
        checkMaxThreshold(patientId, vitals.getTemperaturaAmbientala(), vitals.getValAlarmaTemperaturaAmb(), "Ambient Temperature", "°C");
        checkMinThreshold(patientId, vitals.getTemperaturaAmbientala(), vitals.getValAlarmaTemperaturaAmbMin(), "Ambient Temperature", "°C");
        
        // Check boolean sensors
        checkBooleanSensor(patientId, vitals.getLumina(), vitals.getValAlarmaLumina(), "Light Sensor");
        checkBooleanSensor(patientId, vitals.getGaz(), vitals.getValAlarmaGaz(), "Gas Sensor");
        checkBooleanSensor(patientId, vitals.getUmiditate(), vitals.getValAlarmaUmiditate(), "Humidity Sensor");
        checkBooleanSensor(patientId, vitals.getProximitate(), vitals.getValAlarmaProximitate(), "Proximity Sensor");
    }
    
    private <T extends Comparable<T>> void checkMaxThreshold(Integer patientId, T currentValue, T thresholdValue, String paramName, String unit) {
        if (currentValue == null || thresholdValue == null) {
            return; // Skip check if either value is null
        }
        
        if (currentValue.compareTo(thresholdValue) > 0) {
            String message = String.format("%s exceeds maximum threshold", paramName);
            String currentValueStr = currentValue.toString() + " " + unit;
            String thresholdValueStr = thresholdValue.toString() + " " + unit;
            
            logger.warn("ALARM: Patient {} {} ({} > {})", patientId, message, currentValueStr, thresholdValueStr);
            
            // Create and send the alarm event
            alarmEventService.createAlarmEvent(
                patientId,
                paramName + "_MAX",
                message,
                currentValueStr,
                thresholdValueStr
            );
        }
    }
    
    private <T extends Comparable<T>> void checkMinThreshold(Integer patientId, T currentValue, T thresholdValue, String paramName, String unit) {
        if (currentValue == null || thresholdValue == null) {
            return; // Skip check if either value is null
        }
        
        if (currentValue.compareTo(thresholdValue) < 0) {
            String message = String.format("%s below minimum threshold", paramName);
            String currentValueStr = currentValue.toString() + " " + unit;
            String thresholdValueStr = thresholdValue.toString() + " " + unit;
            
            logger.warn("ALARM: Patient {} {} ({} < {})", patientId, message, currentValueStr, thresholdValueStr);
            
            // Create and send the alarm event
            alarmEventService.createAlarmEvent(
                patientId,
                paramName + "_MIN",
                message,
                currentValueStr,
                thresholdValueStr
            );
        }
    }
    
    private void checkBooleanSensor(Integer patientId, Boolean currentValue, Boolean expectedValue, String sensorName) {
        if (currentValue == null || expectedValue == null) {
            return; // Skip check if either value is null
        }
        
        if (!currentValue.equals(expectedValue)) {
            String message = String.format("%s in unexpected state", sensorName);
            String currentValueStr = currentValue.toString();
            String expectedValueStr = expectedValue.toString();
            
            logger.warn("ALARM: Patient {} {} (current: {}, expected: {})", 
                patientId, message, currentValueStr, expectedValueStr);
            
            // Create and send the alarm event
            alarmEventService.createAlarmEvent(
                patientId,
                sensorName,
                message,
                currentValueStr,
                expectedValueStr
            );
        }
    }
} 