package com.gildas.gestionstock.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class MouvementStock extends AbstractEntity {

    @Column(name = "datemvt")
    private Instant datemvt;

    private BigDecimal quantite;
    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    private TypeMouvementStock typemvt;

    @Column(name = "sourcemvt")
    private SourceMvtStk sourceMvtStk;
}
