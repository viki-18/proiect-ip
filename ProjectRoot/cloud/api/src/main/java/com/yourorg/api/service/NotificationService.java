package com.yourorg.api.service;

import com.yourorg.api.model.AlarmEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Send an alarm notification to all connected clients
     * 
     * @param alarmEvent The alarm event to send
     */
    public void sendAlarmNotification(AlarmEvent alarmEvent) {
        messagingTemplate.convertAndSend("/topic/alarms", alarmEvent);
    }
    
    /**
     * Send a patient-specific alarm notification
     * 
     * @param patientId The patient ID
     * @param alarmEvent The alarm event to send
     */
    public void sendPatientAlarmNotification(Integer patientId, AlarmEvent alarmEvent) {
        messagingTemplate.convertAndSend("/topic/patient/" + patientId + "/alarms", alarmEvent);
    }
    
    /**
     * Send a custom notification message
     * 
     * @param topic The topic to send to
     * @param message The message to send
     */
    public void sendNotification(String topic, Object message) {
        messagingTemplate.convertAndSend(topic, message);
    }
} 