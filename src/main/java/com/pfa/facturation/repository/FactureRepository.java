package com.pfa.facturation.repository;

import com.pfa.facturation.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}