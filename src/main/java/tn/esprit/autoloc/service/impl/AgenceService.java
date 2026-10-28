
package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceService implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle agence ne doit pas avoir d'identifiant"
            );
        }

        verifierAgence(agence);
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id)
                );
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existante = findById(id);
        verifierAgence(agence);

        existante.setNom(agence.getNom());

        return agenceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        agenceRepository.deleteById(id);
    }

    private void verifierAgence(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'agence est obligatoire"
            );
        }
    }
}
