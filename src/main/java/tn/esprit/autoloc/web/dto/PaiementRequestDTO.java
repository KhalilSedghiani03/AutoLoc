package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementRequestDTO(
        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à zéro")
        BigDecimal montant,

        LocalDate datePaiement,

        tn.esprit.autoloc.domain.ModePaiement modePaiement,

        Long idContrat
) {
}
