package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequestDTO;
import tn.esprit.autoloc.web.dto.ClientResponseDTO;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequestDTO dto) {
        Client c = new Client();
        c.setNom(dto.nom());
        c.setPrenom(dto.prenom());
        c.setEmail(dto.email());
        c.setTelephone(dto.telephone());
        c.setNumPermis(dto.numPermis());
        c.setDateInscription(dto.dateInscription());
        return c;
    }
    public ClientResponseDTO toResponseDTO(Client c) {
        return new ClientResponseDTO(c.getIdClient(), c.getNom(), c.getPrenom(), c.getEmail(), c.getTelephone(), c.getNumPermis(), c.getDateInscription());
    }
}
