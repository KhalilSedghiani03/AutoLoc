package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Component
public class ReservationMapper {
    public Reservation toEntity(ReservationRequestDTO dto) {
        Reservation r = new Reservation();
        r.setDateDebut(dto.dateDebut());
        r.setDateFin(dto.dateFin());
        r.setStatut(dto.statut());
        if (dto.idVehicule() != null) {
            Vehicule v = new Vehicule();
            v.setIdVehicule(dto.idVehicule());
            r.setVehicule(v);
        }
        if (dto.idClient() != null) {
            Client c = new Client();
            c.setIdClient(dto.idClient());
            r.setClient(c);
        }
        return r;
    }
    public ReservationResponseDTO toResponseDTO(Reservation r) {
        Long idV = r.getVehicule() != null ? r.getVehicule().getIdVehicule() : null;
        Long idC = r.getClient() != null ? r.getClient().getIdClient() : null;
        return new ReservationResponseDTO(r.getIdReservation(), r.getDateDebut(), r.getDateFin(), r.getStatut(), idV, idC);
    }
}
