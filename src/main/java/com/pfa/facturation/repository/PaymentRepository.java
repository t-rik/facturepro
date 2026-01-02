package com.pfa.facturation.repository;

import com.pfa.facturation.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByFactureId(Long factureId);
}
