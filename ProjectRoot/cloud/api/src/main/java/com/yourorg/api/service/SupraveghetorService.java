package com.yourorg.api.service;

import com.yourorg.api.model.Supraveghetor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SupraveghetorService {
    
    private final Map<Integer, Supraveghetor> supraveghetori = new HashMap<>();
    private int nextId = 1;

    public List<Supraveghetor> getAllSupraveghetori() {
        return new ArrayList<>(supraveghetori.values());
    }

    public Optional<Supraveghetor> getSupraveghetorById(Integer id) {
        return Optional.ofNullable(supraveghetori.get(id));
    }

    public Supraveghetor createSupraveghetor(Supraveghetor supraveghetor) {
        supraveghetor.setIdSupraveghetor(nextId++);
        supraveghetori.put(supraveghetor.getIdSupraveghetor(), supraveghetor);
        return supraveghetor;
    }

    public Supraveghetor updateSupraveghetor(Integer id, Supraveghetor supraveghetor) {
        supraveghetor.setIdSupraveghetor(id);
        supraveghetori.put(id, supraveghetor);
        return supraveghetor;
    }

    public void deleteSupraveghetor(Integer id) {
        supraveghetori.remove(id);
    }
} 