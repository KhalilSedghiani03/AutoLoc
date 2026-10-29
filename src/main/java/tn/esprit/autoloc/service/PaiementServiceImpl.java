
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement create(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        Paiement paiementExistant = findById(id);

        paiementExistant.setMontant(paiement.getMontant());
        paiementExistant.setDatePaiement(paiement.getDatePaiement());
        paiementExistant.setModePaiement(paiement.getModePaiement());

        return paiementRepository.save(paiementExistant);
    }

    @Override
    public void deleteById(Long id) {
        Paiement paiement = findById(id);
        paiementRepository.delete(paiement);
    }
}
