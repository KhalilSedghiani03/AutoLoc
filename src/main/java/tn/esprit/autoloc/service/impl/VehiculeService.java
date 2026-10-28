package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculeService implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id)
                );
    }
    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }
}