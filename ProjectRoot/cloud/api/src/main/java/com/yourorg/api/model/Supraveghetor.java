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
@Table(name = "Supraveghetori")
public class Supraveghetor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supraveghetor")
    private Integer idSupraveghetor;
    
    @Column(name = "id", nullable = false)
    private Integer utilizatorId;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Utilizator utilizator;

    public Supraveghetor() {
    }

    public Supraveghetor(Integer idSupraveghetor, Integer utilizatorId) {
        this.idSupraveghetor = idSupraveghetor;
        this.utilizatorId = utilizatorId;
    }

    public Integer getIdSupraveghetor() {
        return idSupraveghetor;
    }

    public void setIdSupraveghetor(Integer idSupraveghetor) {
        this.idSupraveghetor = idSupraveghetor;
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