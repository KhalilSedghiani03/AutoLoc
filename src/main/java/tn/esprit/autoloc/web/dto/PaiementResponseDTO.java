package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementResponseDTO(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        tn.esprit.autoloc.domain.ModePaiement modePaiement,
        Long idContrat
) {
}
