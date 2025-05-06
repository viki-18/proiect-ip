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
@Table(name = "Istoric_Pacienti")
public class IstoricPacient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_pacient", nullable = false)
    private Integer idPacient;
    
    @ManyToOne
    @JoinColumn(name = "id_pacient", referencedColumnName = "id_pacient", insertable = false, updatable = false)
    private Pacient pacient;
    
    @Column(name = "diagnostice_tratamente", length = 300)
    private String diagnosticeTratamente;
    
    @Column(name = "lista_alergii", length = 200)
    private String listaAlergii;
    
    @Column(name = "recomandari_medicale", length = 300)
    private String recomandariMedicale;
    
    @Column(name = "scheme_medicatie", length = 300)
    private String schemeMedicatie;
    
    @Column(name = "data_adaugare")
    private LocalDateTime dataAdaugare;

    public IstoricPacient() {
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

    public String getDiagnosticeTratamente() {
        return diagnosticeTratamente;
    }

    public void setDiagnosticeTratamente(String diagnosticeTratamente) {
        this.diagnosticeTratamente = diagnosticeTratamente;
    }

    public String getListaAlergii() {
        return listaAlergii;
    }

    public void setListaAlergii(String listaAlergii) {
        this.listaAlergii = listaAlergii;
    }

    public String getRecomandariMedicale() {
        return recomandariMedicale;
    }

    public void setRecomandariMedicale(String recomandariMedicale) {
        this.recomandariMedicale = recomandariMedicale;
    }

    public String getSchemeMedicatie() {
        return schemeMedicatie;
    }

    public void setSchemeMedicatie(String schemeMedicatie) {
        this.schemeMedicatie = schemeMedicatie;
    }

    public LocalDateTime getDataAdaugare() {
        return dataAdaugare;
    }

    public void setDataAdaugare(LocalDateTime dataAdaugare) {
        this.dataAdaugare = dataAdaugare;
    }
} 