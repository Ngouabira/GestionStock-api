package com.gildas.gestionstock.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeClientDTO {

    private Integer id;
    private ArticleDTO article;

    private CommandeClientDTO commandeClient;
    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

}
