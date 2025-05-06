package com.yourorg.api.repository;

import com.yourorg.api.model.IstoricPacient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IstoricPacientRepository extends JpaRepository<IstoricPacient, Integer> {
    List<IstoricPacient> findByIdPacient(Integer idPacient);
    List<IstoricPacient> findByIdPacientOrderByDataAdaugareDesc(Integer idPacient);
} 