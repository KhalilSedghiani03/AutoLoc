
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;

import tn.esprit.autoloc.exception.ResourceNotFoundException;

import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;

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

    // Affecter un véhicule à un équipement
    @Transactional
    @Override
    public VehiculeResponseDTO affecterVehiculeAEquipement(
            Long idVehicule,
            Long idEquipement) {

        Vehicule vehicule = findById(idVehicule);

        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", idEquipement));

        equipement.setVehicule(vehicule);
        equipementRepository.save(equipement);

        return toDTO(vehicule);
    }

    // Affecter une maintenance à un véhicule
    @Override
    @Transactional
    public VehiculeResponseDTO affecterMaintenanceAvehicule(
            String description,
            String immatriculation) {

        Vehicule vehicule = vehiculeRepository
                .findByImmatriculation(immatriculation)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Véhicule introuvable : " + immatriculation));

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setVehicule(vehicule);

        maintenanceRepository.save(maintenance);

        return toDTO(vehicule);
    }

    // Conversion Entity vers DTO
    private VehiculeResponseDTO toDTO(Vehicule vehicule) {
        return new VehiculeResponseDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence() != null
                        ? vehicule.getAgence().getIdAgence()
                        : null
        );
    }
}
