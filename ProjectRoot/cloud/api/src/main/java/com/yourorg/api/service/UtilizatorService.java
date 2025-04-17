package com.yourorg.api.service;

import com.yourorg.api.model.Utilizator;
import com.yourorg.api.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    public List<Utilizator> findAll() {
        return utilizatorRepository.findAll();
    }

    public Optional<Utilizator> findById(Long id) {
        return utilizatorRepository.findById(id);
    }

    public Utilizator save(Utilizator utilizator) {
        return utilizatorRepository.save(utilizator);
    }

    public void deleteById(Long id) {
        utilizatorRepository.deleteById(id);
    }
}