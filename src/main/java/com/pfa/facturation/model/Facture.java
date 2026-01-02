package com.pfa.facturation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Facture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateFacture = new Date();
    private Double montantTotal;
    private Double paidAmount = 0.0;

    @Enumerated(EnumType.STRING)
    private FactureStatus status = FactureStatus.DRAFT;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LigneFacture> lignes;
}