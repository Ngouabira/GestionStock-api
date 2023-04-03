package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.entity.MouvementStock;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MouvementStockMapper {


    public static MouvementStockDTO toDTO(MouvementStock mouvementStock){

        if (mouvementStock==null){
            return null;
        }
        else {
            return MouvementStockDTO.builder()
                    .id(mouvementStock.getId())
                    .datemvt(mouvementStock.getDatemvt())
                    .quantite(mouvementStock.getQuantite())
                    .article(ArticleMapper.toDTO(mouvementStock.getArticle()))
                    .typemvt(mouvementStock.getTypemvt())
                    .sourcemvt(mouvementStock.getSourceMvtStk())
                    .build();
        }
    }

    public static MouvementStock toEntity(MouvementStockDTO mouvementStockDTO){

        if (mouvementStockDTO==null){
            return null;
        }
        else {
            MouvementStock mouvementStock = new MouvementStock();
            mouvementStock.setId(mouvementStockDTO.getId());
            mouvementStock.setDatemvt(mouvementStockDTO.getDatemvt());
            mouvementStock.setQuantite(mouvementStockDTO.getQuantite());
            mouvementStock.setArticle(ArticleMapper.toEntity(mouvementStockDTO.getArticle()));
            mouvementStock.setTypemvt(mouvementStockDTO.getTypemvt());
            mouvementStock.setSourceMvtStk(mouvementStockDTO.getSourcemvt());
            return mouvementStock;
        }
    }
}
