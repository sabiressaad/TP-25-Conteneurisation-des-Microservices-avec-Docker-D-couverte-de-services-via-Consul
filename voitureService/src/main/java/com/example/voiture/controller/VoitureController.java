package com.example.voiture.controller;

import com.example.voiture.model.Voiture;
import com.example.voiture.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;

    @GetMapping
    public List<Voiture> getAllVoitures() {
        return voitureService.findAll();
    }

    @GetMapping("/{id}")
    public Voiture getVoitureById(@PathVariable Long id) {
        return voitureService.findById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<Voiture> getVoituresByClient(@PathVariable Long clientId) {
        return voitureService.findByClientId(clientId);
    }

    @PostMapping
    public Voiture createVoiture(@RequestBody Voiture voiture) {
        return voitureService.save(voiture);
    }

    @PutMapping("/{id}")
    public Voiture updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        voiture.setId(id);
        return voitureService.save(voiture);
    }

    @DeleteMapping("/{id}")
    public void deleteVoiture(@PathVariable Long id) {
        voitureService.deleteById(id);
    }
}
