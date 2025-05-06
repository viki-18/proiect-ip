package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alarme")
public class Alarma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_pacient", nullable = false)
    private Integer idPacient;
    
    @ManyToOne
    @JoinColumn(name = "id_pacient", referencedColumnName = "id_pacient", insertable = false, updatable = false)
    private Pacient pacient;
    
    @Column(name = "durata_alarmei", nullable = false)
    private Integer durataAlarmei;
    
    @Column(name = "perioada_alarmei")
    private Integer perioadaAlarmei;
    
    @Column(nullable = false, length = 50)
    private String alarma;
    
    @Column(name = "detalii_alarma", length = 300)
    private String detaliiAlarma;
    
    @Column(length = 200)
    private String avertizari;
    
    @Column(name = "data_creare")
    private LocalDateTime dataCreare;

    public Alarma() {
    }

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

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Integer getDurataAlarmei() {
        return durataAlarmei;
    }

    public void setDurataAlarmei(Integer durataAlarmei) {
        this.durataAlarmei = durataAlarmei;
    }

    public Integer getPerioadaAlarmei() {
        return perioadaAlarmei;
    }

    public void setPerioadaAlarmei(Integer perioadaAlarmei) {
        this.perioadaAlarmei = perioadaAlarmei;
    }

    public String getAlarma() {
        return alarma;
    }

    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    public String getDetaliiAlarma() {
        return detaliiAlarma;
    }

    public void setDetaliiAlarma(String detaliiAlarma) {
        this.detaliiAlarma = detaliiAlarma;
    }

    public String getAvertizari() {
        return avertizari;
    }

    public void setAvertizari(String avertizari) {
        this.avertizari = avertizari;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(LocalDateTime dataCreare) {
        this.dataCreare = dataCreare;
    }
} 