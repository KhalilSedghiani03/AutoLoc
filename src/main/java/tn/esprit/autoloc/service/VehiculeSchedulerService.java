
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculeSchedulerService {

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;

    // Exécution chaque jour à 01h00
    @Scheduled(cron = "0 0 1 * * *", zone = "Africa/Tunis")
    @Transactional
    public void terminerLocationsEtLibererVehicules() {

        LocalDate aujourdHui =
                LocalDate.now(ZoneId.of("Africa/Tunis"));

        List<Reservation> reservations =
                reservationRepository
                        .findByDateFinLessThanEqualAndStatutIn(
                                aujourdHui,
                                List.of(StatutReservation.CONFIRMEE)
                        );

        for (Reservation reservation : reservations) {

            // Terminer la réservation
            reservation.setStatut(StatutReservation.TERMINEE);

            // Libérer le véhicule associé
            if (reservation.getVehicule() != null) {

                Long idVehicule = reservation.getVehicule().getIdVehicule();

                boolean autreReservationActive =
                        reservationRepository
                                .existsByVehicule_IdVehiculeAndStatutAndDateFinGreaterThanEqual(
                                        idVehicule,
                                        StatutReservation.CONFIRMEE,
                                        aujourdHui
                                );

                if (!autreReservationActive) {
                    reservation.getVehicule()
                            .setStatut(StatutVehicule.DISPONIBLE);

                    vehiculeRepository.save(reservation.getVehicule());
                }
            }

            reservationRepository.save(reservation);

            log.info(
                    "Réservation {} terminée.",
                    reservation.getIdReservation()
            );
        }

        log.info(
                "{} réservation(s) terminée(s).",
                reservations.size()
        );
    }
}
