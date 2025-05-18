package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alarm_Events", schema = "mydb")
public class AlarmEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_pacient", nullable = false)
    private Integer idPacient;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "current_value")
    private String currentValue;
    
    @Column(name = "threshold_value")
    private String thresholdValue;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "acknowledged")
    private Boolean acknowledged;

    // Default constructor
    public AlarmEvent() {
        this.timestamp = LocalDateTime.now();
        this.acknowledged = false;
    }
    
    // Constructor with fields
    public AlarmEvent(Integer idPacient, String type, String message,
                      String currentValue, String thresholdValue) {
        this.idPacient = idPacient;
        this.type = type;
        this.message = message;
        this.currentValue = currentValue;
        this.thresholdValue = thresholdValue;
        this.timestamp = LocalDateTime.now();
        this.acknowledged = false;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(Integer idPacient) {
        this.idPacient = idPacient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Boolean acknowledged) {
        this.acknowledged = acknowledged;
    }
} 