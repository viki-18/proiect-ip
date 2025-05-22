package com.yourorg.api.repository;

import com.yourorg.api.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {
    Optional<Utilizator> findByEmail(String email);
    Utilizator findByNrTelefon(String nrTelefon);
} 