package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceRequestDTO;
import tn.esprit.autoloc.web.dto.AgenceResponseDTO;

@Component
public class AgenceMapper {
    public Agence toEntity(AgenceRequestDTO dto) {
        Agence a = new Agence();
        a.setNom(dto.nom());
        a.setVille(dto.ville());
        a.setAdresse(dto.adresse());
        a.setTelephone(dto.telephone());
        return a;
    }
    public AgenceResponseDTO toResponseDTO(Agence a) {
        return new AgenceResponseDTO(a.getIdAgence(), a.getNom(), a.getVille(), a.getAdresse(), a.getTelephone());
    }
}
