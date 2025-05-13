package com.yourorg.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Pacienti")
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pacient")
    private Integer idPacient;
    
    @Column(name = "id", nullable = false)
    private Integer utilizatorId;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Utilizator utilizator;
    
    private String localitate;
    
    private String strada;
    
    private String nr;
    
    @Column(nullable = false, length = 13)
    private String CNP;
    
    private String profesie;
    
    @Column(name = "id_medic")
    private Integer idMedic;
    
    @ManyToOne
    @JoinColumn(name = "id_medic", referencedColumnName = "id_medic", insertable = false, updatable = false)
    private Medic medic;
    
    @Column(name = "recomandari_de_la_medic", length = 300)
    private String recomandariDeLaMedic;
    
    @Column(name = "id_supraveghetor")
    private Integer idSupraveghetor;
    
    @ManyToOne
    @JoinColumn(name = "id_supraveghetor", referencedColumnName = "id_supraveghetor", insertable = false, updatable = false)
    private Supraveghetor supraveghetor;
    
    @Column(name = "id_ingrijitor")
    private Integer idIngrijitor;
    
    @ManyToOne
    @JoinColumn(name = "id_ingrijitor", referencedColumnName = "id_ingrijitor", insertable = false, updatable = false)
    private Ingrijitor ingrijitor;
    
    @Column(name = "id_smartphone", nullable = false, unique = true)
    private Integer idSmartphone;
    
    @Column(name = "path_rapoarte")
    private String pathRapoarte;

    public Pacient() {
    }

    public Integer getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(Integer idPacient) {
        this.idPacient = idPacient;
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

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getProfesie() {
        return profesie;
    }

    public void setProfesie(String profesie) {
        this.profesie = profesie;
    }

    public Integer getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(Integer idMedic) {
        this.idMedic = idMedic;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public String getRecomandariDeLaMedic() {
        return recomandariDeLaMedic;
    }

    public void setRecomandariDeLaMedic(String recomandariDeLaMedic) {
        this.recomandariDeLaMedic = recomandariDeLaMedic;
    }

    public Integer getIdSupraveghetor() {
        return idSupraveghetor;
    }

    public void setIdSupraveghetor(Integer idSupraveghetor) {
        this.idSupraveghetor = idSupraveghetor;
    }

    public Supraveghetor getSupraveghetor() {
        return supraveghetor;
    }

    public void setSupraveghetor(Supraveghetor supraveghetor) {
        this.supraveghetor = supraveghetor;
    }

    public Integer getIdIngrijitor() {
        return idIngrijitor;
    }

    public void setIdIngrijitor(Integer idIngrijitor) {
        this.idIngrijitor = idIngrijitor;
    }

    public Ingrijitor getIngrijitor() {
        return ingrijitor;
    }

    public void setIngrijitor(Ingrijitor ingrijitor) {
        this.ingrijitor = ingrijitor;
    }

    public Integer getIdSmartphone() {
        return idSmartphone;
    }

    public void setIdSmartphone(Integer idSmartphone) {
        this.idSmartphone = idSmartphone;
    }

    public String getPathRapoarte() {
        return pathRapoarte;
    }

    public void setPathRapoarte(String pathRapoarte) {
        this.pathRapoarte = pathRapoarte;
    }
} 