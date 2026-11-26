package tn.esprit.autoloc.repository;

import tn.esprit.autoloc.domain.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    @Query("""
        SELECT c FROM Contrat c
        WHERE c.reservation.dateFin BETWEEN :debut AND :fin
    """)
    List<Contrat> findByReservationDateFinBetween(
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin
    );
}