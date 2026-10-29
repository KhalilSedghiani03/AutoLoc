package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;
import tn.esprit.autoloc.web.mapper.ContratMapper;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;
    private final ContratMapper contratMapper;

    @GetMapping
    public List<ContratResponseDTO> findAll() {
        return contratService.findAll()
                .stream()
                .map(contratMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ContratResponseDTO findById(@PathVariable Long id) {
        Contrat contrat = contratService.findById(id);
        return contratMapper.toResponseDTO(contrat);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratResponseDTO create(
            @Valid @RequestBody ContratRequestDTO dto) {

        Contrat contrat = contratMapper.toEntity(dto);
        Contrat created = contratService.create(contrat);

        return contratMapper.toResponseDTO(created);
    }

    @PutMapping("/{id}")
    public ContratResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ContratRequestDTO dto) {

        Contrat contrat = contratMapper.toEntity(dto);
        Contrat updated = contratService.update(id, contrat);

        return contratMapper.toResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}