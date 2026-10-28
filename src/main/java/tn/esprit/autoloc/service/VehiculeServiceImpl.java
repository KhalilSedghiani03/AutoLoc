
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule save(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant"
            );
        }

        verifierTarif(vehicule);
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
    public Vehicule update(Vehicule vehicule) {
        if (vehicule.getIdVehicule() == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du véhicule est obligatoire"
            );
        }

        Vehicule existant = findById(vehicule.getIdVehicule());
        verifierTarif(vehicule);

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        vehiculeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }

    private void verifierTarif(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null
                && vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }
    }
}
