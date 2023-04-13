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
public class LigneVente extends  AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "vente_id")
    private Vente vente;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;
}
