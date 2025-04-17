package com.yourorg.api.repository;

import com.yourorg.api.model.IstoricPacient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstoricPacientRepository extends JpaRepository<IstoricPacient, Long> {
}