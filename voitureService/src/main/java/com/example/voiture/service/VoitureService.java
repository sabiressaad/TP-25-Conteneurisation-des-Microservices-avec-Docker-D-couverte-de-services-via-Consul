package com.example.voiture.service;

import com.example.voiture.model.Voiture;
import com.example.voiture.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureService {
    
    @Autowired
    private VoitureRepository voitureRepository;

    public List<Voiture> findAll() {
        return voitureRepository.findAll();
    }

    public Voiture findById(Long id) {
        return voitureRepository.findById(id).orElse(null);
    }

    public List<Voiture> findByClientId(Long clientId) {
        return voitureRepository.findByIdClient(clientId);
    }

    public Voiture save(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    public void deleteById(Long id) {
        voitureRepository.deleteById(id);
    }
}
