
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation reservation) {
        verifierDates(reservation);

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existante = findById(id);
        verifierDates(reservation);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        existante.setVehicule(reservation.getVehicule());
        existante.setContrat(reservation.getContrat());
        existante.setClient(reservation.getClient());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        reservationRepository.deleteById(id);
    }

    private void verifierDates(Reservation reservation) {
        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
