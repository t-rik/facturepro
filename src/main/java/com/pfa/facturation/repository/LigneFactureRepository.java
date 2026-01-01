package com.pfa.facturation.repository;

import com.pfa.facturation.model.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {
}