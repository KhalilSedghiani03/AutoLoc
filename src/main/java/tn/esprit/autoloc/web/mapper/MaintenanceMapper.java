package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDTO;

@Component
public class MaintenanceMapper {
    public Maintenance toEntity(MaintenanceRequestDTO dto) {
        Maintenance m = new Maintenance();
        m.setDateDebut(dto.dateDebut());
        m.setDateFin(dto.dateFin());
        m.setDescription(dto.description());
        if (dto.idVehicule() != null) {
            Vehicule v = new Vehicule();
            v.setIdVehicule(dto.idVehicule());
            m.setVehicule(v);
        }
        return m;
    }
    public MaintenanceResponseDTO toResponseDTO(Maintenance m) {
        Long idV = m.getVehicule() != null ? m.getVehicule().getIdVehicule() : null;
        return new MaintenanceResponseDTO(m.getIdMaintenance(), m.getDateDebut(), m.getDateFin(), m.getDescription(), idV);
    }
}
