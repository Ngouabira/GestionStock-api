package com.gildas.gestionstock.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeFournisseurDTO {

    private Integer id;
    private ArticleDTO article;
    private CommandeFournisseurDTO commandeFournisseur;

    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
}
