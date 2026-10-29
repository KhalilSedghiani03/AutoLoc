package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.PaiementRequestDTO;
import tn.esprit.autoloc.web.dto.PaiementResponseDTO;

@Component
public class PaiementMapper {
    public Paiement toEntity(PaiementRequestDTO dto) {
        Paiement p = new Paiement();
        p.setMontant(dto.montant());
        p.setDatePaiement(dto.datePaiement());
        p.setModePaiement(dto.modePaiement());
        if (dto.idContrat() != null) {
            Contrat c = new Contrat();
            c.setIdContrat(dto.idContrat());
            p.setContrat(c);
        }
        return p;
    }
    public PaiementResponseDTO toResponseDTO(Paiement p) {
        Long idC = p.getContrat() != null ? p.getContrat().getIdContrat() : null;
        return new PaiementResponseDTO(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(), p.getModePaiement(), idC);
    }
}
