package com.yourorg.api.dto;

import java.time.LocalDateTime;

public class SemneVitaleValuesDTO {
    private Integer puls;
    private Integer tensiuneArteriala;
    private Float temperaturaCorporala;
    private Float greutate;
    private Float glicemie;
    private Boolean lumina;
    private Float temperaturaAmbientala;
    private Boolean gaz;
    private Boolean umiditate;
    private Boolean proximitate;
    private LocalDateTime dataInregistrare;

    public SemneVitaleValuesDTO() {
    }

    public Integer getPuls() {
        return puls;
    }

    public void setPuls(Integer puls) {
        this.puls = puls;
    }

    public Integer getTensiuneArteriala() {
        return tensiuneArteriala;
    }

    public void setTensiuneArteriala(Integer tensiuneArteriala) {
        this.tensiuneArteriala = tensiuneArteriala;
    }

    public Float getTemperaturaCorporala() {
        return temperaturaCorporala;
    }

    public void setTemperaturaCorporala(Float temperaturaCorporala) {
        this.temperaturaCorporala = temperaturaCorporala;
    }

    public Float getGreutate() {
        return greutate;
    }

    public void setGreutate(Float greutate) {
        this.greutate = greutate;
    }

    public Float getGlicemie() {
        return glicemie;
    }

    public void setGlicemie(Float glicemie) {
        this.glicemie = glicemie;
    }

    public Boolean getLumina() {
        return lumina;
    }

    public void setLumina(Boolean lumina) {
        this.lumina = lumina;
    }

    public Float getTemperaturaAmbientala() {
        return temperaturaAmbientala;
    }

    public void setTemperaturaAmbientala(Float temperaturaAmbientala) {
        this.temperaturaAmbientala = temperaturaAmbientala;
    }

    public Boolean getGaz() {
        return gaz;
    }

    public void setGaz(Boolean gaz) {
        this.gaz = gaz;
    }

    public Boolean getUmiditate() {
        return umiditate;
    }

    public void setUmiditate(Boolean umiditate) {
        this.umiditate = umiditate;
    }

    public Boolean getProximitate() {
        return proximitate;
    }

    public void setProximitate(Boolean proximitate) {
        this.proximitate = proximitate;
    }

    public LocalDateTime getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(LocalDateTime dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }
} 