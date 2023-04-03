package com.gildas.gestionstock.dto;

import com.gildas.gestionstock.entity.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDTO {
    private Integer id;
    private String code;
    private String description;
    private BigDecimal prixUnitaireHt;
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTtc;
    private String photo;
    private CategorieDTO categorie;
}
