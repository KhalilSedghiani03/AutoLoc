package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehiculeRequestDTO(
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20)
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50)
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50)
        String modele,

        @NotNull(message = "La catégorie est obligatoire")
        tn.esprit.autoloc.domain.CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.01", message = "Le tarif doit être supérieur à zéro")
        BigDecimal tarifJournalier,

        @NotNull(message = "Le statut est obligatoire")
        tn.esprit.autoloc.domain.StatutVehicule statut,

        Long idAgence
) {
}
