package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratService implements IContratService {

    private final IContratRepository contratRepository;

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
}