package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratRequestDTO(

        @NotNull(message = "La date de signature est obligatoire")
        @PastOrPresent(message = "La date ne peut pas être dans le futur")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total est obligatoire")
        @DecimalMin(value = "0.01",
                message = "Le montant doit être supérieur à zéro")
        BigDecimal montantTotal,

        boolean valide
) {
}