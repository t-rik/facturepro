package com.pfa.facturation.service;

import com.pfa.facturation.model.Facture;
import com.pfa.facturation.model.FactureStatus;
import com.pfa.facturation.model.Payment;
import com.pfa.facturation.repository.FactureRepository;
import com.pfa.facturation.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FactureRepository factureRepository;

    public Payment recordPayment(Long factureId, Payment payment) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        payment.setFacture(facture);
        Payment saved = paymentRepository.save(payment);

        // Update facture's paid amount
        double newPaidAmount = (facture.getPaidAmount() != null ? facture.getPaidAmount() : 0) + payment.getAmount();
        facture.setPaidAmount(newPaidAmount);

        // Update status if fully paid
        if (newPaidAmount >= facture.getMontantTotal()) {
            facture.setStatus(FactureStatus.PAID);
        }

        factureRepository.save(facture);
        return saved;
    }

    public List<Payment> getPaymentsByFacture(Long factureId) {
        return paymentRepository.findByFactureId(factureId);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
