package tn.esprit.autoloc.web.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationWithContratRequestDTO {

    // Informations de la réservation
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Informations nécessaires pour retrouver le client et le véhicule
    private Long idClient;
    private Long idVehicule;

    // Informations du contrat
    private LocalDate dateSignature;
}