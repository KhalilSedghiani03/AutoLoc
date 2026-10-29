package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;

public record ReservationResponseDTO(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        tn.esprit.autoloc.domain.StatutReservation statut,
        Long idVehicule,
        Long idClient
) {
}
