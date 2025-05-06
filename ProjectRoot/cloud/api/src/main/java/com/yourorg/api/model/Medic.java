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
@Table(name = "Medici", schema = "mydb")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medic")
    private Integer idMedic;
    
    @Column(name = "id", nullable = false)
    private Integer utilizatorId;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Utilizator utilizator;

    public Medic() {
    }

    public Medic(Integer idMedic, Integer utilizatorId) {
        this.idMedic = idMedic;
        this.utilizatorId = utilizatorId;
    }

    public Integer getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(Integer idMedic) {
        this.idMedic = idMedic;
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