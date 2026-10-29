package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeRequestDTO;
import tn.esprit.autoloc.web.dto.EmployeResponseDTO;
import tn.esprit.autoloc.web.mapper.EmployeMapper;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {
    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @GetMapping
    public List<EmployeResponseDTO> findAll() {
        return employeService.findAll().stream().map(employeMapper::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public EmployeResponseDTO findById(@PathVariable Long id) {
        return employeMapper.toResponseDTO(employeService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponseDTO create(@Valid @RequestBody EmployeRequestDTO dto) {
        Employe e = employeMapper.toEntity(dto);
        return employeMapper.toResponseDTO(employeService.create(e));
    }

    @PutMapping("/{id}")
    public EmployeResponseDTO update(@PathVariable Long id, @Valid @RequestBody EmployeRequestDTO dto) {
        Employe e = employeMapper.toEntity(dto);
        return employeMapper.toResponseDTO(employeService.update(id, e));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
