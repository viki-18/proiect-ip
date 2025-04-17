package com.yourorg.api.service;

import com.yourorg.api.model.SemnVital;
import com.yourorg.api.repository.SemnVitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemnVitalService {

    @Autowired
    private SemnVitalRepository semnVitalRepository;

    public List<SemnVital> findAll() {
        return semnVitalRepository.findAll();
    }

    public Optional<SemnVital> findById(Long id) {
        return semnVitalRepository.findById(id);
    }

    public SemnVital save(SemnVital semnVital) {
        return semnVitalRepository.save(semnVital);
    }

    public void deleteById(Long id) {
        semnVitalRepository.deleteById(id);
    }
}