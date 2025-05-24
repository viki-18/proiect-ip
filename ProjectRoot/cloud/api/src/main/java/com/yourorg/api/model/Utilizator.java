package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Utilizatori")
public class Utilizator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 30)
    private String email;
    
    @Column(name = "nr_telefon", unique = true, nullable = false, length = 12)
    private String nrTelefon;
    
    @Column(name = "Tip_utilizator", nullable = false, length = 1)
    private String tipUtilizator;
    
    @Column(nullable = false, length = 30)
    private String parola;

    @Column(length = 50)
    private String nume;

    @Column(length = 50)
    private String prenume;

    @Column
    private Integer varsta;

    @Column(length = 1)
    private String gen;

    @Column(length = 100)
    private String specializare;
    
    public Utilizator() {
    }

    public Utilizator(Integer id, String email, String nrTelefon, String tipUtilizator, String parola) {
        this.id = id;
        this.email = email;
        this.nrTelefon = nrTelefon;
        this.tipUtilizator = tipUtilizator;
        this.parola = parola;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getTipUtilizator() {
        return tipUtilizator;
    }

    public void setTipUtilizator(String tipUtilizator) {
        this.tipUtilizator = tipUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }
} 