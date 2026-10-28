
package tn.esprit.autoloc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // Ajouter un véhicule
    @PostMapping
    public ResponseEntity<Vehicule> save(@RequestBody Vehicule vehicule) {
        return ResponseEntity.ok(vehiculeService.save(vehicule));
    }

    // Rechercher un véhicule par ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    // Récupérer tous les véhicules
    @GetMapping
    public ResponseEntity<List<Vehicule>> findAll() {
        return ResponseEntity.ok(vehiculeService.findAll());
    }

    // Modifier un véhicule
    @PutMapping
    public ResponseEntity<Vehicule> update(@RequestBody Vehicule vehicule) {
        return ResponseEntity.ok(vehiculeService.update(vehicule));
    }

    // Supprimer un véhicule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!vehiculeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        vehiculeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Vérifier si un véhicule existe
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.existsById(id));
    }
}
