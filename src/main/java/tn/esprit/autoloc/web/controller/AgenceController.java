package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceRequestDTO;
import tn.esprit.autoloc.web.dto.AgenceResponseDTO;
import tn.esprit.autoloc.web.mapper.AgenceMapper;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final IAgenceService agenceService;
    private final AgenceMapper agenceMapper;

    @GetMapping
    public List<AgenceResponseDTO> findAll() {
        return agenceService.findAll().stream().map(agenceMapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public AgenceResponseDTO findById(@PathVariable Long id) {
        return agenceMapper.toResponseDTO(agenceService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceResponseDTO create(@Valid @RequestBody AgenceRequestDTO dto) {
        Agence a = agenceMapper.toEntity(dto);
        return agenceMapper.toResponseDTO(agenceService.create(a));
    }

    @PutMapping("/{id}")
    public AgenceResponseDTO update(@PathVariable Long id, @Valid @RequestBody AgenceRequestDTO dto) {
        Agence a = agenceMapper.toEntity(dto);
        return agenceMapper.toResponseDTO(agenceService.update(id, a));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
