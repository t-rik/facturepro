package com.pfa.facturation.service;

import com.pfa.facturation.model.Facture;
import com.pfa.facturation.model.FactureStatus;
import com.pfa.facturation.model.LigneFacture;
import com.pfa.facturation.repository.ClientRepository;
import com.pfa.facturation.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {
    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Facture creerFacture(Facture facture) {
        // Resolve client if ID is provided
        if (facture.getClient() != null && facture.getClient().getId() != null) {
            facture.setClient(clientRepository.findById(facture.getClient().getId()).orElse(null));
        }

        double total = 0;
        if(facture.getLignes() != null) {
            for (LigneFacture l : facture.getLignes()) {
                total += l.getPrixUnitaire() * l.getQuantite();
            }
        }
        facture.setMontantTotal(total);
        return factureRepository.save(facture);
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture updateFacture(Long id, Facture factureDetails) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        // Resolve client if ID is provided
        if (factureDetails.getClient() != null && factureDetails.getClient().getId() != null) {
            facture.setClient(clientRepository.findById(factureDetails.getClient().getId()).orElse(null));
        }

        facture.setLignes(factureDetails.getLignes());

        // Recalculate total
        double total = 0;
        if (facture.getLignes() != null) {
            for (LigneFacture l : facture.getLignes()) {
                total += l.getPrixUnitaire() * l.getQuantite();
            }
        }
        facture.setMontantTotal(total);

        if (factureDetails.getStatus() != null) {
            facture.setStatus(factureDetails.getStatus());
        }

        return factureRepository.save(facture);
    }

    public Facture updateStatus(Long id, FactureStatus status) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));
        facture.setStatus(status);
        return factureRepository.save(facture);
    }

    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }
}