package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.web.dto.ContratRequestDTO;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;

@Component
public class ContratMapper {

    public Contrat toEntity(ContratRequestDTO dto) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());
        return contrat;
    }

    public ContratResponseDTO toResponseDTO(Contrat contrat) {
        return new ContratResponseDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide()
        );
    }
}