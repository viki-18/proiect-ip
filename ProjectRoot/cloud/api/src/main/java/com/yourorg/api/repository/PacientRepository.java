package com.yourorg.api.repository;

import com.yourorg.api.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PacientRepository extends JpaRepository<Pacient, Integer> {
    Pacient findByUtilizatorId(Integer utilizatorId);
    List<Pacient> findByIdMedic(Integer idMedic);
    List<Pacient> findByIdIngrijitor(Integer idIngrijitor);
    List<Pacient> findByIdSupraveghetor(Integer idSupraveghetor);
    
    @Query("SELECT p.idPacient FROM Pacient p")
    List<Integer> findAllIds();
} 