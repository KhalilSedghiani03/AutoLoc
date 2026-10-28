
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

    // Ajouter une agence
    @PostMapping
    public ResponseEntity<Agence> create(@RequestBody Agence agence) {
        return ResponseEntity.ok(agenceService.create(agence));
    }

    // Rechercher une agence par ID
    @GetMapping("/{id}")
    public ResponseEntity<Agence> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agenceService.findById(id));
    }

    // Récupérer toutes les agences
    @GetMapping
    public ResponseEntity<List<Agence>> findAll() {
        return ResponseEntity.ok(agenceService.findAll());
    }

    // Modifier une agence
    @PutMapping("/{id}")
    public ResponseEntity<Agence> update(
            @PathVariable Long id,
            @RequestBody Agence agence) {
        return ResponseEntity.ok(agenceService.update(id, agence));
    }

    // Supprimer une agence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
