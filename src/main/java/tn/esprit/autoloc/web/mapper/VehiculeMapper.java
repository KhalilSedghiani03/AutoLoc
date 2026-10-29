package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.web.dto.VehiculeResponseDTO;

@Component
public class VehiculeMapper {
    public Vehicule toEntity(VehiculeRequestDTO dto) {
        Vehicule v = new Vehicule();
        v.setImmatriculation(dto.immatriculation());
        v.setMarque(dto.marque());
        v.setModele(dto.modele());
        v.setCategorie(dto.categorie());
        v.setTarifJournalier(dto.tarifJournalier());
        v.setStatut(dto.statut());
        if (dto.idAgence() != null) {
            Agence a = new Agence();
            a.setIdAgence(dto.idAgence());
            v.setAgence(a);
        }
        return v;
    }
    public VehiculeResponseDTO toResponseDTO(Vehicule v) {
        Long idA = v.getAgence() != null ? v.getAgence().getIdAgence() : null;
        return new VehiculeResponseDTO(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele(), v.getCategorie(), v.getTarifJournalier(), v.getStatut(), idA);
    }
}
