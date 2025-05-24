package com.yourorg.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Ingrijitori")
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

    public String getNume() {
        return utilizator != null ? utilizator.getNume() : null;
    }

    public String getPrenume() {
        return utilizator != null ? utilizator.getPrenume() : null;
    }

    public Integer getVarsta() {
        return utilizator != null ? utilizator.getVarsta() : null;
    }

    public String getGen() {
        return utilizator != null ? utilizator.getGen() : null;
    }

    public String getSpecializare() {
        return utilizator != null ? utilizator.getSpecializare() : null;
    }
}