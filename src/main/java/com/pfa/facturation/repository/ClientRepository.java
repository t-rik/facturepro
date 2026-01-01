package com.pfa.facturation.repository;

import com.pfa.facturation.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}