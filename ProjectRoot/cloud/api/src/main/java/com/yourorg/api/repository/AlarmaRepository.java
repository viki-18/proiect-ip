package com.yourorg.api.repository;

import com.yourorg.api.model.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlarmaRepository extends JpaRepository<Alarma, Integer> {
    List<Alarma> findByIdPacient(Integer idPacient);
    List<Alarma> findByIdPacientAndAlarma(Integer idPacient, String alarma);
} 