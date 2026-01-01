package com.pfa.facturation.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LigneFacture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private Double prixUnitaire;
    private Integer quantite;
}