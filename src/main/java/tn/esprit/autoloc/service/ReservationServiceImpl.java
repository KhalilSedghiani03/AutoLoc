
package tn.esprit.autoloc.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;

import tn.esprit.autoloc.exception.ResourceNotFoundException;

import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IContratRepository;

import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;

    // CREATE
    @Override
    public Reservation create(Reservation reservation) {
        verifierDates(reservation);

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant"
            );
        }

        return reservationRepository.save(reservation);
    }

    // FIND BY ID
    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    // FIND ALL
    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    // UPDATE
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

    // DELETE
    @Override
    public void deleteById(Long id) {
        findById(id);
        reservationRepository.deleteById(id);
    }

    // VERIFICATION DES DATES
    private void verifierDates(Reservation reservation) {
        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }


    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(
            Long idReservation,
            String nom,
            String prenom
    ) {

        // 1. Rechercher la réservation
        Reservation reservation = findById(idReservation);

        // 2. Rechercher le client par nom et prénom
        Client client = clientRepository.findByNomAndPrenom(nom, prenom)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Client introuvable : " + nom + " " + prenom
                        )
                );

        // 3. Affecter le client à la réservation
        reservation.setClient(client);

        // 4. Enregistrer la réservation
        Reservation reservationEnregistree =
                reservationRepository.save(reservation);

        // 5. Retourner le DTO
        return new ReservationResponseDTO(
                reservationEnregistree.getIdReservation(),
                reservationEnregistree.getDateDebut(),
                reservationEnregistree.getDateFin(),
                reservationEnregistree.getStatut(),
                reservationEnregistree.getVehicule() != null
                        ? reservationEnregistree.getVehicule().getIdVehicule()
                        : null,
                client.getIdClient()
        );
    }


    // CREER UNE RESERVATION ET SON CONTRAT ASSOCIE
    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(
            ReservationWithContratRequestDTO requestDTO
    ) {

        // 1. Vérifier les informations obligatoires
        if (requestDTO.getDateDebut() == null
                || requestDTO.getDateFin() == null
                || requestDTO.getDateSignature() == null
                || requestDTO.getIdClient() == null
                || requestDTO.getIdVehicule() == null) {

            throw new IllegalArgumentException(
                    "Tous les champs sont obligatoires"
            );
        }

        // 2. Vérifier les dates
        if (requestDTO.getDateFin().isBefore(requestDTO.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin doit être après la date de début"
            );
        }

        // 3. Rechercher le client
        Client client = clientRepository.findById(requestDTO.getIdClient())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client", requestDTO.getIdClient()
                        )
                );

        // 4. Rechercher le véhicule
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.getIdVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", requestDTO.getIdVehicule()
                        )
                );

        // 5. Créer la réservation
        Reservation reservation = new Reservation();

        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        // 6. Enregistrer la réservation
        Reservation reservationEnregistree =
                reservationRepository.save(reservation);

        // 7. Créer le contrat
        Contrat contrat = new Contrat();

        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setValide(true);

        // 8. Associer le contrat à la réservation
        contrat.setReservation(reservationEnregistree);
        reservationEnregistree.setContrat(contrat);

        // 9. Enregistrer le contrat
        contratRepository.save(contrat);

        // 10. Retourner le DTO
        return new ReservationResponseDTO(
                reservationEnregistree.getIdReservation(),
                reservationEnregistree.getDateDebut(),
                reservationEnregistree.getDateFin(),
                reservationEnregistree.getStatut(),
                vehicule.getIdVehicule(),
                client.getIdClient()
        );
    }
}
