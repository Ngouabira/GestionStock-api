package com.gildas.gestionstock.dto;

import com.gildas.gestionstock.entity.SourceMvtStk;
import com.gildas.gestionstock.entity.TypeMouvementStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class MouvementStockDTO {

    private Integer id;
    private Instant datemvt;
    private BigDecimal quantite;
    private ArticleDTO article;
    private TypeMouvementStock typemvt;
    private SourceMvtStk sourcemvt;

}
