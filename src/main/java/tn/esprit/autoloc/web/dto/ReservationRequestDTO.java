package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull(message = "La date de début est obligatoire")
        @PastOrPresent(message = "La date de début ne peut pas être dans le futur")
        LocalDate dateDebut,

        LocalDate dateFin,

        tn.esprit.autoloc.domain.StatutReservation statut,

        Long idVehicule,

        Long idClient
) {
}
