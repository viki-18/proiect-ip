package com.yourorg.api.service;

import com.yourorg.api.model.Administrator;
import com.yourorg.api.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> findById(Long id) {
        return administratorRepository.findById(id);
    }

    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public void deleteById(Long id) {
        administratorRepository.deleteById(id);
    }
}