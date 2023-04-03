package com.gildas.gestionstock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class Vente extends AbstractEntity {

    private String code;

    @Column(name = "datevente")
    private Instant dateVente;
    private String commentaire;
}
