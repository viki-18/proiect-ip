package com.yourorg.api.service;

import com.yourorg.api.model.Alarma;
import com.yourorg.api.repository.AlarmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmaService {

    @Autowired
    private AlarmaRepository alarmaRepository;

    public List<Alarma> findAll() {
        return alarmaRepository.findAll();
    }

    public Optional<Alarma> findById(Long id) {
        return alarmaRepository.findById(id);
    }

    public Alarma save(Alarma alarma) {
        return alarmaRepository.save(alarma);
    }

    public void deleteById(Long id) {
        alarmaRepository.deleteById(id);
    }
}