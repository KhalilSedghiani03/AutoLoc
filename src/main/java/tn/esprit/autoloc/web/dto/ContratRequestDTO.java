
package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratRequestDTO(
        LocalDate dateSignature,
        BigDecimal montantTotal,
        boolean valide
) {
}
