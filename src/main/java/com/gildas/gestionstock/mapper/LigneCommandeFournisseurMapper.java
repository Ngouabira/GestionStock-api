package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.entity.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LigneCommandeFournisseurMapper {

    public static LigneCommandeFournisseurDTO toDTO(LigneCommandeFournisseur ligneCommandeFournisseur) {
        if (ligneCommandeFournisseur == null){
            return null;
        }
        else {
            return LigneCommandeFournisseurDTO.builder()
                    .id(ligneCommandeFournisseur.getId())
                    .article(ArticleMapper.toDTO(ligneCommandeFournisseur.getArticle()))
                    .commandeFournisseur(CommandeFournisseurMapper.toDTO(ligneCommandeFournisseur.getCommandeFournisseur()))
                    .quantite(ligneCommandeFournisseur.getQuantite())
                    .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                    .build();
        }
    }

    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDTO ligneCommandeFournisseurDTO) {
        if (ligneCommandeFournisseurDTO == null){
            return null;
        }
        else {
            LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();
            ligneCommandeFournisseur.setId(ligneCommandeFournisseurDTO.getId());
            ligneCommandeFournisseur.setArticle(ArticleMapper.toEntity(ligneCommandeFournisseurDTO.getArticle()));
            ligneCommandeFournisseur.setCommandeFournisseur(CommandeFournisseurMapper.toEntity(ligneCommandeFournisseurDTO.getCommandeFournisseur()));
            ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDTO.getQuantite());
            ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDTO.getPrixUnitaire());
            return ligneCommandeFournisseur;
        }

    }
}
