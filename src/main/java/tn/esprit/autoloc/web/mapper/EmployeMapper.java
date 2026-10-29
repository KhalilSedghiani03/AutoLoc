package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeRequestDTO;
import tn.esprit.autoloc.web.dto.EmployeResponseDTO;

@Component
public class EmployeMapper {
    public Employe toEntity(EmployeRequestDTO dto) {
        Employe e = new Employe();
        e.setNom(dto.nom());
        e.setPrenom(dto.prenom());
        e.setRole(dto.role());
        if (dto.idAgence() != null) {
            Agence a = new Agence();
            a.setIdAgence(dto.idAgence());
            e.setAgence(a);
        }
        return e;
    }
    public EmployeResponseDTO toResponseDTO(Employe e) {
        Long idAgence = e.getAgence() != null ? e.getAgence().getIdAgence() : null;
        return new EmployeResponseDTO(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(), idAgence);
    }
}
