package tn.esprit.autoloc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    // CREATE
    @PostMapping
    public Agence create(@RequestBody Agence agence) {
        return agenceService.save(agence);
    }

    // READ - tous les agences
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // READ - une agence par ID
    @GetMapping("/{id}")
    public ResponseEntity<Agence> findById(@PathVariable Long id) {

        return agenceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Agence> update(
            @PathVariable Long id,
            @RequestBody Agence agence) {

        if (!agenceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        agence.setIdAgence(id);

        return ResponseEntity.ok(agenceService.update(agence));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!agenceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        agenceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}