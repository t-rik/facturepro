package com.pfa.facturation.service;

import com.pfa.facturation.model.Facture;
import com.pfa.facturation.model.LigneFacture;
import com.pfa.facturation.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService {
    @Autowired
    private FactureRepository factureRepository;

    public Facture creerFacture(Facture facture) {
        //
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
}