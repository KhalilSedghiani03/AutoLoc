
package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratResponseDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant"
            );
        }

        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Contrat", id)
                );
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat contratExistant = findById(id);

        contratExistant.setDateSignature(contrat.getDateSignature());
        contratExistant.setMontantTotal(contrat.getMontantTotal());
        contratExistant.setValide(contrat.isValide());

        return contratRepository.save(contratExistant);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        contratRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return contratRepository.existsById(id);
    }

    @Override
    @Transactional
    public ContratResponseDTO affecterContratAReservation(
            LocalDate dateSignature,
            LocalDate dateDebut,
            LocalDate dateFin
    ) {
        // 1. Rechercher la réservation par ses dates
        Reservation reservation = reservationRepository
                .findByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Réservation introuvable"
                        )
                );

        // 2. Vérifier si la réservation possède déjà un contrat
        if (reservation.getContrat() != null) {
            throw new IllegalArgumentException(
                    "Cette réservation possède déjà un contrat"
            );
        }

        // 3. Créer le contrat
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setValide(true);

        // 4. Établir la relation bidirectionnelle
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        // 5. Enregistrer le contrat
        Contrat contratEnregistre = contratRepository.save(contrat);

        // 6. Enregistrer la réservation
        reservationRepository.save(reservation);

        // 7. Retourner le DTO
        return new ContratResponseDTO(
                contratEnregistre.getIdContrat(),
                contratEnregistre.getDateSignature(),
                contratEnregistre.getMontantTotal(),
                contratEnregistre.isValide()
        );
    }
}
