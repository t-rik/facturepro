package com.pfa.facturation.controller;

import com.pfa.facturation.model.Facture;
import com.pfa.facturation.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin("*")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @PostMapping
    public Facture ajouterFacture(@RequestBody Facture facture) {
        return factureService.creerFacture(facture);
    }

    @GetMapping
    public List<Facture> listerFactures() {
        return factureService.getAllFactures();
    }
}