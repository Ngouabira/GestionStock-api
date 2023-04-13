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
public class LigneCommandeClient extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "commandeclient_id")
    private CommandeClient commandeClient;

    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;
}

