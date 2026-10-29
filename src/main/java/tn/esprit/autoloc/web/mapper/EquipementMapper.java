package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.EquipementRequestDTO;
import tn.esprit.autoloc.web.dto.EquipementResponseDTO;

@Component
public class EquipementMapper {
    public Equipement toEntity(EquipementRequestDTO dto) {
        Equipement e = new Equipement();
        e.setLibelle(dto.libelle());
        if (dto.idVehicule() != null) {
            Vehicule v = new Vehicule();
            v.setIdVehicule(dto.idVehicule());
            e.setVehicule(v);
        }
        return e;
    }
    public EquipementResponseDTO toResponseDTO(Equipement e) {
        Long idV = e.getVehicule() != null ? e.getVehicule().getIdVehicule() : null;
        return new EquipementResponseDTO(e.getIdEquipement(), e.getLibelle(), idV);
    }
}
