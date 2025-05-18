package com.yourorg.api.repository;

import com.yourorg.api.model.AlarmEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Integer> {
    List<AlarmEvent> findByIdPacient(Integer idPacient);
    List<AlarmEvent> findByIdPacientAndAcknowledged(Integer idPacient, Boolean acknowledged);
    List<AlarmEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<AlarmEvent> findByIdPacientOrderByTimestampDesc(Integer idPacient);
} 