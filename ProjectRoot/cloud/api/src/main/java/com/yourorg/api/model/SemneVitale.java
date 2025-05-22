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
@Table(name = "Semne_Vitale")
public class SemneVitale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_pacient", nullable = false)
    private Integer idPacient;
    
    @ManyToOne
    @JoinColumn(name = "id_pacient", referencedColumnName = "id_pacient", insertable = false, updatable = false)
    private Pacient pacient;
    
    private Integer puls;
    
    @Column(name = "val_alarma_puls")
    private Integer valAlarmaPuls;
    
    @Column(name = "val_alarma_puls_min")
    private Integer valAlarmaPulsMin;
    
    @Column(name = "tensiune_arteriala")
    private Integer tensiuneArteriala;
    
    @Column(name = "val_alarma_tensiune")
    private Integer valAlarmaTensiune;
    
    @Column(name = "val_alarma_tensiune_min")
    private Integer valAlarmaTensiuneMin;
    
    @Column(name = "temperatura_corporala")
    private Float temperaturaCorporala;
    
    @Column(name = "val_alarma_temperatura")
    private Float valAlarmaTemperatura;
    
    @Column(name = "val_alarma_temperatura_min")
    private Float valAlarmaTemperaturaMin;
    
    private Float greutate;
    
    @Column(name = "val_alarma_greutate")
    private Float valAlarmaGreutate;
    
    @Column(name = "val_alarma_greutate_min")
    private Float valAlarmaGreutateMin;
    
    private Float glicemie;
    
    @Column(name = "val_alarma_glicemie")
    private Float valAlarmaGlicemie;
    
    @Column(name = "val_alarma_glicemie_min")
    private Float valAlarmaGlicemieMin;
    
    private Boolean lumina;
    
    @Column(name = "val_alarma_lumina")
    private Boolean valAlarmaLumina;
    
    @Column(name = "temperatura_ambientala")
    private Float temperaturaAmbientala;
    
    @Column(name = "val_alarma_temperatura_amb")
    private Float valAlarmaTemperaturaAmb;
    
    @Column(name = "val_alarma_temperatura_amb_min")
    private Float valAlarmaTemperaturaAmbMin;
    
    private Boolean gaz;
    
    @Column(name = "val_alarma_gaz")
    private Boolean valAlarmaGaz;
    
    private Boolean umiditate;
    
    @Column(name = "val_alarma_umiditate")
    private Boolean valAlarmaUmiditate;
    
    private Boolean proximitate;
    
    @Column(name = "val_alarma_proximitate")
    private Boolean valAlarmaProximitate;
    
    @Column(name = "data_inregistrare")
    private LocalDateTime dataInregistrare;

    public SemneVitale() {
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

    public Integer getPuls() {
        return puls;
    }

    public void setPuls(Integer puls) {
        this.puls = puls;
    }

    public Integer getValAlarmaPuls() {
        return valAlarmaPuls;
    }

    public void setValAlarmaPuls(Integer valAlarmaPuls) {
        this.valAlarmaPuls = valAlarmaPuls;
    }

    public Integer getValAlarmaPulsMin() {
        return valAlarmaPulsMin;
    }

    public void setValAlarmaPulsMin(Integer valAlarmaPulsMin) {
        this.valAlarmaPulsMin = valAlarmaPulsMin;
    }

    public Integer getTensiuneArteriala() {
        return tensiuneArteriala;
    }

    public void setTensiuneArteriala(Integer tensiuneArteriala) {
        this.tensiuneArteriala = tensiuneArteriala;
    }

    public Integer getValAlarmaTensiune() {
        return valAlarmaTensiune;
    }

    public void setValAlarmaTensiune(Integer valAlarmaTensiune) {
        this.valAlarmaTensiune = valAlarmaTensiune;
    }

    public Integer getValAlarmaTensiuneMin() {
        return valAlarmaTensiuneMin;
    }

    public void setValAlarmaTensiuneMin(Integer valAlarmaTensiuneMin) {
        this.valAlarmaTensiuneMin = valAlarmaTensiuneMin;
    }

    public Float getTemperaturaCorporala() {
        return temperaturaCorporala;
    }

    public void setTemperaturaCorporala(Float temperaturaCorporala) {
        this.temperaturaCorporala = temperaturaCorporala;
    }

    public Float getValAlarmaTemperatura() {
        return valAlarmaTemperatura;
    }

    public void setValAlarmaTemperatura(Float valAlarmaTemperatura) {
        this.valAlarmaTemperatura = valAlarmaTemperatura;
    }

    public Float getValAlarmaTemperaturaMin() {
        return valAlarmaTemperaturaMin;
    }

    public void setValAlarmaTemperaturaMin(Float valAlarmaTemperaturaMin) {
        this.valAlarmaTemperaturaMin = valAlarmaTemperaturaMin;
    }

    public Float getGreutate() {
        return greutate;
    }

    public void setGreutate(Float greutate) {
        this.greutate = greutate;
    }

    public Float getValAlarmaGreutate() {
        return valAlarmaGreutate;
    }

    public void setValAlarmaGreutate(Float valAlarmaGreutate) {
        this.valAlarmaGreutate = valAlarmaGreutate;
    }

    public Float getValAlarmaGreutateMin() {
        return valAlarmaGreutateMin;
    }

    public void setValAlarmaGreutateMin(Float valAlarmaGreutateMin) {
        this.valAlarmaGreutateMin = valAlarmaGreutateMin;
    }

    public Float getGlicemie() {
        return glicemie;
    }

    public void setGlicemie(Float glicemie) {
        this.glicemie = glicemie;
    }

    public Float getValAlarmaGlicemie() {
        return valAlarmaGlicemie;
    }

    public void setValAlarmaGlicemie(Float valAlarmaGlicemie) {
        this.valAlarmaGlicemie = valAlarmaGlicemie;
    }

    public Float getValAlarmaGlicemieMin() {
        return valAlarmaGlicemieMin;
    }

    public void setValAlarmaGlicemieMin(Float valAlarmaGlicemieMin) {
        this.valAlarmaGlicemieMin = valAlarmaGlicemieMin;
    }

    public Boolean getLumina() {
        return lumina;
    }

    public void setLumina(Boolean lumina) {
        this.lumina = lumina;
    }

    public Boolean getValAlarmaLumina() {
        return valAlarmaLumina;
    }

    public void setValAlarmaLumina(Boolean valAlarmaLumina) {
        this.valAlarmaLumina = valAlarmaLumina;
    }

    public Float getTemperaturaAmbientala() {
        return temperaturaAmbientala;
    }

    public void setTemperaturaAmbientala(Float temperaturaAmbientala) {
        this.temperaturaAmbientala = temperaturaAmbientala;
    }

    public Float getValAlarmaTemperaturaAmb() {
        return valAlarmaTemperaturaAmb;
    }

    public void setValAlarmaTemperaturaAmb(Float valAlarmaTemperaturaAmb) {
        this.valAlarmaTemperaturaAmb = valAlarmaTemperaturaAmb;
    }

    public Float getValAlarmaTemperaturaAmbMin() {
        return valAlarmaTemperaturaAmbMin;
    }

    public void setValAlarmaTemperaturaAmbMin(Float valAlarmaTemperaturaAmbMin) {
        this.valAlarmaTemperaturaAmbMin = valAlarmaTemperaturaAmbMin;
    }

    public Boolean getGaz() {
        return gaz;
    }

    public void setGaz(Boolean gaz) {
        this.gaz = gaz;
    }

    public Boolean getValAlarmaGaz() {
        return valAlarmaGaz;
    }

    public void setValAlarmaGaz(Boolean valAlarmaGaz) {
        this.valAlarmaGaz = valAlarmaGaz;
    }

    public Boolean getUmiditate() {
        return umiditate;
    }

    public void setUmiditate(Boolean umiditate) {
        this.umiditate = umiditate;
    }

    public Boolean getValAlarmaUmiditate() {
        return valAlarmaUmiditate;
    }

    public void setValAlarmaUmiditate(Boolean valAlarmaUmiditate) {
        this.valAlarmaUmiditate = valAlarmaUmiditate;
    }

    public Boolean getProximitate() {
        return proximitate;
    }

    public void setProximitate(Boolean proximitate) {
        this.proximitate = proximitate;
    }

    public Boolean getValAlarmaProximitate() {
        return valAlarmaProximitate;
    }

    public void setValAlarmaProximitate(Boolean valAlarmaProximitate) {
        this.valAlarmaProximitate = valAlarmaProximitate;
    }

    public LocalDateTime getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(LocalDateTime dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }
} 