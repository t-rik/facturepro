package com.pfa.facturation.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String telephone;
}