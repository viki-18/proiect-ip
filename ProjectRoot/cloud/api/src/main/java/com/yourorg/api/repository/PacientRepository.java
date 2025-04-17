package com.yourorg.api.repository;

import com.yourorg.api.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacientRepository extends JpaRepository<Pacient, Long> {
}