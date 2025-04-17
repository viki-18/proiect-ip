package com.yourorg.api.service;

import com.yourorg.api.model.Pacient;
import com.yourorg.api.repository.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacientService {

    @Autowired
    private PacientRepository pacientRepository;

    public List<Pacient> findAll() {
        return pacientRepository.findAll();
    }

    public Optional<Pacient> findById(Long id) {
        return pacientRepository.findById(id);
    }

    public Pacient save(Pacient pacient) {
        return pacientRepository.save(pacient);
    }

    public void deleteById(Long id) {
        pacientRepository.deleteById(id);
    }
}