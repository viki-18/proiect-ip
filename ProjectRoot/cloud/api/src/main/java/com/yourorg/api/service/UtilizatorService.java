package com.yourorg.api.service;

import com.yourorg.api.model.Utilizator;
import com.yourorg.api.model.Medic;
import com.yourorg.api.model.Pacient;
import com.yourorg.api.model.Ingrijitor;
import com.yourorg.api.model.Supraveghetor;
import com.yourorg.api.repository.UtilizatorRepository;
import com.yourorg.api.repository.MedicRepository;
import com.yourorg.api.repository.PacientRepository;
import com.yourorg.api.repository.IngrijitorRepository;
import com.yourorg.api.repository.SupraveghetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private IngrijitorRepository ingrijitorRepository;

    @Autowired
    private SupraveghetorRepository supraveghetorRepository;

    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public Optional<Utilizator> getUtilizatorById(Integer id) {
        return utilizatorRepository.findById(id);
    }

    @Transactional
    public Object createUtilizatorWithType(Utilizator utilizator, Map<String, Object> additionalData) {
        // Salvează utilizatorul și obține ID-ul generat
        Utilizator savedUtilizator = utilizatorRepository.save(utilizator);
        
        // Creează tipul specific în funcție de tipul utilizatorului
        switch (utilizator.getTipUtilizator()) {
            case "M":
                Medic medic = new Medic();
                medic.setUtilizatorId(savedUtilizator.getId());
                return medicRepository.save(medic);
                
            case "P":
                Pacient pacient = new Pacient();
                pacient.setUtilizatorId(savedUtilizator.getId());
                if (additionalData != null) {
                    pacient.setCNP((String) additionalData.get("CNP"));
                    pacient.setLocalitate((String) additionalData.get("localitate"));
                    pacient.setStrada((String) additionalData.get("strada"));
                    pacient.setNr((String) additionalData.get("nr"));
                    pacient.setProfesie((String) additionalData.get("profesie"));
                    if (additionalData.get("idSmartphone") != null) {
                        pacient.setIdSmartphone(((Number) additionalData.get("idSmartphone")).intValue());
                    }
                }
                return pacientRepository.save(pacient);
                
            case "I":
                Ingrijitor ingrijitor = new Ingrijitor();
                ingrijitor.setUtilizatorId(savedUtilizator.getId());
                return ingrijitorRepository.save(ingrijitor);
                
            case "S":
                Supraveghetor supraveghetor = new Supraveghetor();
                supraveghetor.setUtilizatorId(savedUtilizator.getId());
                return supraveghetorRepository.save(supraveghetor);
                
            default:
                return savedUtilizator;
        }
    }

    @Transactional
    public Utilizator updateUtilizator(Integer id, Utilizator utilizator) {
        if (!utilizatorRepository.existsById(id)) {
            throw new RuntimeException("Utilizator not found with id: " + id);
        }
        utilizator.setId(id);
        return utilizatorRepository.save(utilizator);
    }

    @Transactional
    public void deleteUtilizator(Integer id) {
        if (!utilizatorRepository.existsById(id)) {
            throw new RuntimeException("Utilizator not found with id: " + id);
        }
        utilizatorRepository.deleteById(id);
    }
} 