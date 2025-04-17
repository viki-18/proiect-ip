package com.yourorg.api.service;

import com.yourorg.api.model.Supraveghetor;
import com.yourorg.api.repository.SupraveghetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupraveghetorService {

    @Autowired
    private SupraveghetorRepository supraveghetorRepository;

    public List<Supraveghetor> findAll() {
        return supraveghetorRepository.findAll();
    }

    public Optional<Supraveghetor> findById(Long id) {
        return supraveghetorRepository.findById(id);
    }

    public Supraveghetor save(Supraveghetor supraveghetor) {
        return supraveghetorRepository.save(supraveghetor);
    }

    public void deleteById(Long id) {
        supraveghetorRepository.deleteById(id);
    }
}