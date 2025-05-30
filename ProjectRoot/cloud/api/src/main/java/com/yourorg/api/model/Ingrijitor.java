package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Ingrijitori", schema = "mydb")
public class Ingrijitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrijitor")
    private Integer idIngrijitor;
    
    @Column(name = "id", nullable = false)
    private Integer utilizatorId;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Utilizator utilizator;

    public Ingrijitor() {
    }

    public Ingrijitor(Integer idIngrijitor, Integer utilizatorId) {
        this.idIngrijitor = idIngrijitor;
        this.utilizatorId = utilizatorId;
    }

    public Integer getIdIngrijitor() {
        return idIngrijitor;
    }

    public void setIdIngrijitor(Integer idIngrijitor) {
        this.idIngrijitor = idIngrijitor;
    }

    public Integer getUtilizatorId() {
        return utilizatorId;
    }

    public void setUtilizatorId(Integer utilizatorId) {
        this.utilizatorId = utilizatorId;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }
}