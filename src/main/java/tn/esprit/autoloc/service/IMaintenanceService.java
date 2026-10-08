package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;
import java.util.Optional;

public interface IMaintenanceService {

    Maintenance save(Maintenance maintenance);

    Maintenance update(Maintenance maintenance);

    Optional<Maintenance> findById(Long id);

    List<Maintenance> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}