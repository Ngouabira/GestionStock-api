package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.LigneVenteDTO;
import com.gildas.gestionstock.entity.LigneVente;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LigneVenteMapper {

    public static LigneVenteDTO toDTO(LigneVente ligneVente){

        if (ligneVente==null){
            return null;
        }
        else{
            return LigneVenteDTO.builder()
                    .id(ligneVente.getId())
                    .vente(VenteMapper.toDTO(ligneVente.getVente()))
                    .quantite(ligneVente.getQuantite())
                    .prixUnitaire(ligneVente.getPrixUnitaire())
                    .article(ArticleMapper.toDTO(ligneVente.getArticle()))
                    .build();
        }
    }

    public static LigneVente toEntity(LigneVenteDTO ligneVenteDTO){


        if (ligneVenteDTO==null){
            return null;
        }
        else {
            LigneVente ligneVente = new LigneVente();
            ligneVente.setId(ligneVenteDTO.getId());
            ligneVente.setVente(VenteMapper.toEntity(ligneVenteDTO.getVente()));
            ligneVente.setQuantite(ligneVenteDTO.getQuantite());
            ligneVente.setPrixUnitaire(ligneVenteDTO.getPrixUnitaire());
            ligneVente.setArticle(ArticleMapper.toEntity(ligneVenteDTO.getArticle()));
            return ligneVente;
        }
    }
}
