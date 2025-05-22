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
    private PacientRepository repository;

    public List<Pacient> getAllPacienti() {
        return repository.findAll();
    }

    public Optional<Pacient> getPacientById(Integer id) {
        return repository.findById(id);
    }

    public Pacient createPacient(Pacient pacient) {
        return repository.save(pacient);
    }

    public Pacient updatePacient(Integer id, Pacient pacient) {
        pacient.setIdPacient(id);
        return repository.save(pacient);
    }

    public void deletePacient(Integer id) {
        repository.deleteById(id);
    }
    
    public boolean sendToExternalSystem(Pacient pacient, String target) {
        // Here would be the logic to send data to external systems
        // For now, we're just returning true to simulate success
        // Using the Utilizator connection to get the user's email, or just using ID if not available
        String name = pacient.getUtilizator() != null ? 
                      "Email: " + pacient.getUtilizator().getEmail() : 
                      "ID: " + pacient.getIdPacient();
        System.out.println("Sending patient " + name + " data to " + target);
        return true;
    }
} 