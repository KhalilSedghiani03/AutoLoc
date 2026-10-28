
package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementService implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        if (equipement.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel équipement ne doit pas avoir d'identifiant"
            );
        }

        verifierEquipement(equipement);
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id)
                );
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        Equipement existant = findById(id);

        verifierEquipement(equipement);

        existant.setLibelle(equipement.getLibelle());
        existant.setVehicule(equipement.getVehicule());

        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        equipementRepository.deleteById(id);
    }

    private void verifierEquipement(Equipement equipement) {
        if (equipement.getLibelle() == null
                || equipement.getLibelle().isBlank()) {
            throw new IllegalArgumentException(
                    "Le libellé de l'équipement est obligatoire"
            );
        }
    }
}
