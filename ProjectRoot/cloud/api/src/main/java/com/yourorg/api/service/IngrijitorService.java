package com.yourorg.api.service;

import com.yourorg.api.model.Ingrijitor;
import com.yourorg.api.repository.IngrijitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngrijitorService {

    @Autowired
    private IngrijitorRepository repository;

    public List<Ingrijitor> getAllIngrijitori() {
        return repository.findAll();
    }

    public Optional<Ingrijitor> getIngrijitorById(Long id) {
        return repository.findById(id);
    }

    public Ingrijitor createIngrijitor(Ingrijitor ingrijitor) {
        return repository.save(ingrijitor);
    }

    public Ingrijitor updateIngrijitor(Long id, Ingrijitor ingrijitor) {
        ingrijitor.setId(id);
        return repository.save(ingrijitor);
    }

    public void deleteIngrijitor(Long id) {
        repository.deleteById(id);
    }
}