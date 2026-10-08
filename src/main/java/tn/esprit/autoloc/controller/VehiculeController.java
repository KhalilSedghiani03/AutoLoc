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

    // CREATE
    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.save(vehicule);
    }

    // READ - tous les véhicules
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    // READ - un véhicule par ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> findById(@PathVariable Long id) {

        return vehiculeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> update(
            @PathVariable Long id,
            @RequestBody Vehicule vehicule) {

        if (!vehiculeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        vehicule.setIdVehicule(id);

        return ResponseEntity.ok(vehiculeService.update(vehicule));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!vehiculeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        vehiculeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}