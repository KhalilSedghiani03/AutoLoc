package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {
    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @GetMapping
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeService.findAll().stream().map(vehiculeMapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public VehiculeResponseDTO findById(@PathVariable Long id) {
        return vehiculeMapper.toResponseDTO(vehiculeService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDTO create(@Valid @RequestBody VehiculeRequestDTO dto) {
        Vehicule v = vehiculeMapper.toEntity(dto);
        return vehiculeMapper.toResponseDTO(vehiculeService.save(v));
    }

    @PutMapping("/{id}")
    public VehiculeResponseDTO update(@PathVariable Long id, @Valid @RequestBody VehiculeRequestDTO dto) {
        Vehicule v = vehiculeMapper.toEntity(dto);
        v.setIdVehicule(id);
        return vehiculeMapper.toResponseDTO(vehiculeService.update(v));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
