package com.yourorg.api.repository;

import com.yourorg.api.model.SemneVitale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SemneVitaleRepository extends JpaRepository<SemneVitale, Integer> {
    List<SemneVitale> findByIdPacient(Integer idPacient);
    List<SemneVitale> findTop10ByIdPacientOrderByDataInregistrareDesc(Integer idPacient);
} 