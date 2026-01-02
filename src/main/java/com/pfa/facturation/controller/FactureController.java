package com.pfa.facturation.controller;

import com.pfa.facturation.model.Facture;
import com.pfa.facturation.model.FactureStatus;
import com.pfa.facturation.service.FactureService;
import com.pfa.facturation.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public Facture ajouterFacture(@RequestBody Facture facture) {
        return factureService.creerFacture(facture);
    }

    @GetMapping
    public List<Facture> listerFactures() {
        return factureService.getAllFactures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return factureService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getFacturePdf(@PathVariable Long id) {
        return factureService.getById(id)
                .map(facture -> {
                    byte[] pdf = pdfService.generateFacturePdf(facture);
                    return ResponseEntity.ok()
                            .header("Content-Type", "application/pdf")
                            .header("Content-Disposition", "attachment; filename=facture_" + id + ".pdf")
                            .body(pdf);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.updateFacture(id, facture));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Facture> updateStatus(@PathVariable Long id, @RequestParam FactureStatus status) {
        return ResponseEntity.ok(factureService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public void supprimerFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }
}