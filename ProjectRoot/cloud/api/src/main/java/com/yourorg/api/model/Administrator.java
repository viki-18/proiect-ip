package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administratori")
public class Administrator {
    
    @Id
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Utilizator utilizator;
    
    public Administrator() {
    }

    public Administrator(Integer id, Utilizator utilizator) {
        this.id = id;
        this.utilizator = utilizator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }
} 