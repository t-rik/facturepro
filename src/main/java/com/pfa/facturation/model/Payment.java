package com.pfa.facturation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private Date paymentDate = new Date();
    private String paymentMethod; // CASH, CARD, TRANSFER, CHECK

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
}
