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
} 