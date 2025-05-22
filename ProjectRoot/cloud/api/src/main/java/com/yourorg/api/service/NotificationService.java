package com.yourorg.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourorg.api.handler.NotificationWebSocketHandler;
import com.yourorg.api.model.AlarmEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationService(NotificationWebSocketHandler webSocketHandler, ObjectMapper objectMapper) {
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
    }

    /**
     * Send an alarm notification to all connected clients
     * 
     * @param alarmEvent The alarm event to send
     */
    public void sendAlarmNotification(AlarmEvent alarmEvent) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", "ALARM");
            payload.put("topic", "alarms");
            payload.put("data", alarmEvent);
            
            String message = objectMapper.writeValueAsString(payload);
            webSocketHandler.broadcastMessage(message);
            logger.debug("Broadcast alarm notification: {}", alarmEvent.getId());
        } catch (JsonProcessingException e) {
            logger.error("Error serializing alarm notification: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Send a patient-specific alarm notification
     * 
     * @param patientId The patient ID
     * @param alarmEvent The alarm event to send
     */
    public void sendPatientAlarmNotification(Integer patientId, AlarmEvent alarmEvent) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", "ALARM");
            payload.put("topic", "patient/" + patientId + "/alarms");
            payload.put("data", alarmEvent);
            
            String message = objectMapper.writeValueAsString(payload);
            webSocketHandler.broadcastMessage(message);
            logger.debug("Broadcast patient-specific alarm notification. Patient: {}, Alarm: {}", 
                    patientId, alarmEvent.getId());
        } catch (JsonProcessingException e) {
            logger.error("Error serializing patient-specific alarm notification: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Send a custom notification message
     * 
     * @param topic The topic of the notification
     * @param message The message to send
     */
    public void sendNotification(String topic, Object data) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", "NOTIFICATION");
            payload.put("topic", topic);
            payload.put("data", data);
            
            String message = objectMapper.writeValueAsString(payload);
            webSocketHandler.broadcastMessage(message);
            logger.debug("Broadcast custom notification to topic: {}", topic);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing custom notification: {}", e.getMessage(), e);
        }
    }
} 