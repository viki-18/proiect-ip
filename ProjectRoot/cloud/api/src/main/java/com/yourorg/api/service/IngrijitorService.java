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

    public Optional<Ingrijitor> getIngrijitorById(Integer id) {
        return repository.findById(id);
    }

    public Ingrijitor createIngrijitor(Ingrijitor ingrijitor) {
        return repository.save(ingrijitor);
    }

    public Ingrijitor updateIngrijitor(Integer id, Ingrijitor ingrijitor) {
        ingrijitor.setIdIngrijitor(id);
        return repository.save(ingrijitor);
    }

    public void deleteIngrijitor(Integer id) {
        repository.deleteById(id);
    }
    
    public boolean sendToExternalSystem(Ingrijitor ingrijitor, String target) {
        // Here would be the logic to send data to external systems
        // For now, we're just returning true to simulate success
        String name = ingrijitor.getUtilizator() != null ? 
                      "Email: " + ingrijitor.getUtilizator().getEmail() : 
                      "ID: " + ingrijitor.getIdIngrijitor();
        System.out.println("Sending ingrijitor " + name + " data to " + target);
        return true;
    }
}