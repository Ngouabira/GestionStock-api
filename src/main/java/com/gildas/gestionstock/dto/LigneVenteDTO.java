package com.gildas.gestionstock.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneVenteDTO {

    private Integer id;
    private VenteDTO vente;

    private ArticleDTO article;
    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

}
