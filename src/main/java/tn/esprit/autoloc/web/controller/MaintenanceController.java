package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc.web.mapper.MaintenanceMapper;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper;

    @GetMapping
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceService.findAll().stream().map(maintenanceMapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public MaintenanceResponseDTO findById(@PathVariable Long id) {
        return maintenanceMapper.toResponseDTO(maintenanceService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDTO create(@Valid @RequestBody MaintenanceRequestDTO dto) {
        Maintenance m = maintenanceMapper.toEntity(dto);
        return maintenanceMapper.toResponseDTO(maintenanceService.create(m));
    }

    @PutMapping("/{id}")
    public MaintenanceResponseDTO update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequestDTO dto) {
        Maintenance m = maintenanceMapper.toEntity(dto);
        return maintenanceMapper.toResponseDTO(maintenanceService.update(id, m));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
