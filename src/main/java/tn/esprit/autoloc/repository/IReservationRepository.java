package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByDateDebutAndDateFin(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    List<Reservation> findByDateFinLessThanEqualAndStatutIn(
            LocalDate dateFin,
            Collection<StatutReservation> statuts
    );
    boolean existsByVehicule_IdVehiculeAndStatutAndDateFinGreaterThanEqual(
            Long idVehicule,
            StatutReservation statut,
            LocalDate date
    );
}