package com.yourorg.api.service;

import com.yourorg.api.model.Medic;
import com.yourorg.api.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicService {

    @Autowired
    private MedicRepository repository;

    public List<Medic> getAllMedici() {
        return repository.findAll();
    }

    public Optional<Medic> getMedicById(Integer id) {
        return repository.findById(id);
    }

    public Medic createMedic(Medic medic) {
        return repository.save(medic);
    }

    public Medic updateMedic(Integer id, Medic medic) {
        medic.setIdMedic(id);
        return repository.save(medic);
    }

    public void deleteMedic(Integer id) {
        repository.deleteById(id);
    }
    
    public boolean sendToExternalSystem(Medic medic, String target) {
        // Here would be the logic to send data to external systems
        // For now, we're just returning true to simulate success
        String name = medic.getUtilizator() != null ? 
                      "Email: " + medic.getUtilizator().getEmail() : 
                      "ID: " + medic.getIdMedic();
        System.out.println("Sending medic " + name + " data to " + target);
        return true;
    }
} 