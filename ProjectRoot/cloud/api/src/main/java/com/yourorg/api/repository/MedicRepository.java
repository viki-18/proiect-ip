package com.yourorg.api.repository;

import com.yourorg.api.model.Medic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicRepository extends JpaRepository<Medic, Integer> {
    Medic findByUtilizatorId(Integer utilizatorId);
} 