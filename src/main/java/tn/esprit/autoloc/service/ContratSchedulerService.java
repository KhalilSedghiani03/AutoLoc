package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratSchedulerService {

    private final IContratRepository contratRepository;

    @Scheduled(cron = "0 0 7 * * MON-FRI", zone = "Africa/Tunis")
    public void verifierContratsExpirantBientot() {

        LocalDate aujourdHui = LocalDate.now(ZoneId.of("Africa/Tunis"));
        LocalDate dans48Heures = aujourdHui.plusDays(2);

        List<Contrat> contrats =
                contratRepository.findByReservationDateFinBetween(
                        aujourdHui,
                        dans48Heures
                );

        if (contrats.isEmpty()) {
            log.info("Aucun contrat n'arrive à échéance dans les prochaines 48 heures.");
            return;
        }

        log.info("{} contrat(s) arrivent à échéance :", contrats.size());

        contrats.forEach(contrat ->
                log.info(
                        "Contrat ID : {} | Date de fin : {}",
                        contrat.getIdContrat(),
                        contrat.getReservation().getDateFin()
                )
        );
    }
}