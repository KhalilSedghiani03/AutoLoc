
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

import java.util.List;

public interface IVehiculeService {

    Vehicule save(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Vehicule vehicule);

    void deleteById(Long id);

    boolean existsById(Long id);

    VehiculeResponseDTO affecterVehiculeAEquipement(
            Long idVehicule,
            Long idEquipement
    );

    VehiculeResponseDTO affecterMaintenanceAvehicule(
            String description,
            String immatriculation
    );
}
