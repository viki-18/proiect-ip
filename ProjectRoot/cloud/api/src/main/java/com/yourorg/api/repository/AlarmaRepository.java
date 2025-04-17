package com.yourorg.api.repository;

import com.yourorg.api.model.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmaRepository extends JpaRepository<Alarma, Long> {
}