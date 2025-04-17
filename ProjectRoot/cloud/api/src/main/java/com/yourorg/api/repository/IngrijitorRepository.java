package com.yourorg.api.repository;

import com.yourorg.api.model.Ingrijitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngrijitorRepository extends JpaRepository<Ingrijitor, Long> {
}