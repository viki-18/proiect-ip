package com.yourorg.api.service;

import com.yourorg.api.model.IstoricPacient;
import com.yourorg.api.repository.IstoricPacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IstoricPacientService {

    @Autowired
    private IstoricPacientRepository istoricPacientRepository;

    public List<IstoricPacient> findAll() {
        return istoricPacientRepository.findAll();
    }

    public Optional<IstoricPacient> findById(Long id) {
        return istoricPacientRepository.findById(id);
    }

    public IstoricPacient save(IstoricPacient istoricPacient) {
        return istoricPacientRepository.save(istoricPacient);
    }

    public void deleteById(Long id) {
        istoricPacientRepository.deleteById(id);
    }
}