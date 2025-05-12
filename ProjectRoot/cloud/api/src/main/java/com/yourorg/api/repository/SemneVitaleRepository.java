package com.yourorg.api.repository;

import com.yourorg.api.model.SemneVitale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SemneVitaleRepository extends JpaRepository<SemneVitale, Integer> {
    List<SemneVitale> findByIdPacient(Integer idPacient);
    List<SemneVitale> findByIdPacientOrderByDataInregistrareDesc(Integer idPacient);
    Optional<SemneVitale> findFirstByIdPacientOrderByDataInregistrareDesc(Integer idPacient);
} 