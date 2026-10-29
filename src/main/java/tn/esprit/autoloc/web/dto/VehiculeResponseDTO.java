package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;

public record VehiculeResponseDTO(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        tn.esprit.autoloc.domain.CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        tn.esprit.autoloc.domain.StatutVehicule statut,
        Long idAgence
) {
}
