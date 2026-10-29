package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MaintenanceRequestDTO(
        @NotNull(message = "La date de début est obligatoire")
        @PastOrPresent(message = "La date de début ne peut pas être dans le futur")
        LocalDate dateDebut,

        LocalDate dateFin,

        @NotBlank(message = "La description est obligatoire")
        String description,

        Long idVehicule
) {
}
