package com.yourorg.api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.yourorg.api.model.AlarmEvent;
import com.yourorg.api.service.AlarmEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/alarme")
public class AlarmEventController {

    @Autowired
    private AlarmEventService service;

    @GetMapping
    public List<AlarmEvent> getAllAlarms() {
        return service.getAlarmsBetweenDates(
            LocalDateTime.now().minusDays(7), 
            LocalDateTime.now()
        );
    }

    @GetMapping("/pacient/{idPacient}")
    public List<AlarmEvent> getAlarmsByPatient(@PathVariable Integer idPacient) {
        return service.getAllAlarmsByPatient(idPacient);
    }

    @GetMapping("/pacient/{idPacient}/active")
    public List<AlarmEvent> getActiveAlarmsByPatient(@PathVariable Integer idPacient) {
        return service.getUnacknowledgedAlarmsByPatient(idPacient);
    }

    @PutMapping("/{id}/acknowledge")
    public ResponseEntity<AlarmEvent> acknowledgeAlarm(@PathVariable Integer id) {
        Optional<AlarmEvent> alarm = service.acknowledgeAlarm(id);
        return alarm.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Manually trigger an alarm for a patient
     * @param idPacient the patient ID
     * @param alarmData the alarm data containing title, parameter name, and value
     * @return the created alarm event
     */
    @PostMapping("/trigger/{idPacient}")
    public AlarmEvent triggerAlarm(
            @PathVariable Integer idPacient,
            @RequestBody Map<String, Object> alarmData) {
        
        String title = (String) alarmData.get("title");
        String parameterName = (String) alarmData.get("parameterName");
        String value = alarmData.get("value").toString();
        String thresholdValue = alarmData.containsKey("thresholdValue") ? 
                               alarmData.get("thresholdValue").toString() : "N/A";
        
        String message = String.format("%s: %s is out of normal range", title, parameterName);
        
        return service.createAlarmEvent(
            idPacient,
            parameterName,
            message,
            value,
            thresholdValue
        );
    }

    @GetMapping("/interval")
    public List<AlarmEvent> getAlarmsByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        
        return service.getAlarmsBetweenDates(startDate, endDate);
    }
} 