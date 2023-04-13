package com.gildas.gestionstock.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class LigneCommandeFournisseur extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "commandefournisseur_id")
    private CommandeFournisseur commandeFournisseur;

    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;
}

