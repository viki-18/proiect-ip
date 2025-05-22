package com.yourorg.api.service;

import com.yourorg.api.model.AlarmEvent;
import com.yourorg.api.repository.AlarmEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlarmEventService {
    
    @Autowired
    private AlarmEventRepository repository;
    
    @Autowired
    private NotificationService notificationService;
    
    public AlarmEvent createAlarmEvent(Integer idPacient, String type, String message, 
                                     String currentValue, String thresholdValue) {
        AlarmEvent event = new AlarmEvent(idPacient, type, message, currentValue, thresholdValue);
        AlarmEvent savedEvent = repository.save(event);
        
        // Send notifications via WebSocket
        notificationService.sendAlarmNotification(savedEvent);
        notificationService.sendPatientAlarmNotification(idPacient, savedEvent);
        
        return savedEvent;
    }
    
    public List<AlarmEvent> getAllAlarmsByPatient(Integer idPacient) {
        return repository.findByIdPacientOrderByTimestampDesc(idPacient);
    }
    
    public List<AlarmEvent> getUnacknowledgedAlarmsByPatient(Integer idPacient) {
        return repository.findByIdPacientAndAcknowledged(idPacient, false);
    }
    
    public Optional<AlarmEvent> acknowledgeAlarm(Integer alarmId) {
        Optional<AlarmEvent> alarmOpt = repository.findById(alarmId);
        
        if (alarmOpt.isPresent()) {
            AlarmEvent alarm = alarmOpt.get();
            alarm.setAcknowledged(true);
            AlarmEvent updatedAlarm = repository.save(alarm);
            
            // Send notification that alarm was acknowledged
            notificationService.sendAlarmNotification(updatedAlarm);
            notificationService.sendPatientAlarmNotification(updatedAlarm.getIdPacient(), updatedAlarm);
            
            return Optional.of(updatedAlarm);
        }
        
        return Optional.empty();
    }
    
    public List<AlarmEvent> getAlarmsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return repository.findByTimestampBetween(start, end);
    }
} 