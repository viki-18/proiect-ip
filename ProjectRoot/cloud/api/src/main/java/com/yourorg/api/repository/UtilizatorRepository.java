package com.yourorg.api.repository;

import com.yourorg.api.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {
    Utilizator findByEmail(String email);
    Utilizator findByNrTelefon(String nrTelefon);
} 