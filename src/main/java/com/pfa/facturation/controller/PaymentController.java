package com.pfa.facturation.controller;

import com.pfa.facturation.model.Payment;
import com.pfa.facturation.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/facture/{factureId}")
    public ResponseEntity<Payment> recordPayment(@PathVariable Long factureId, @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.recordPayment(factureId, payment));
    }

    @GetMapping("/facture/{factureId}")
    public ResponseEntity<List<Payment>> getPaymentsByFacture(@PathVariable Long factureId) {
        return ResponseEntity.ok(paymentService.getPaymentsByFacture(factureId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
