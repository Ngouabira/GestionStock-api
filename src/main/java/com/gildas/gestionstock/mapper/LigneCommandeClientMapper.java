package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.entity.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LigneCommandeClientMapper {

    public static LigneCommandeClientDTO toDTO(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null){
            return null;
        }
        else {
            return LigneCommandeClientDTO.builder()
                    .id(ligneCommandeClient.getId())
                    .article(ArticleMapper.toDTO(ligneCommandeClient.getArticle()))
                    .commandeClient(CommandeClientMapper.toDTO(ligneCommandeClient.getCommandeClient()))
                    .quantite(ligneCommandeClient.getQuantite())
                    .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                    .build();
        }
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDTO ligneCommandeClientDTO) {
        if (ligneCommandeClientDTO == null){
            return null;
        }
        else {
            LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
            ligneCommandeClient.setId(ligneCommandeClientDTO.getId());
            ligneCommandeClient.setArticle(ArticleMapper.toEntity(ligneCommandeClientDTO.getArticle()));
            ligneCommandeClient.setCommandeClient(CommandeClientMapper.toEntity(ligneCommandeClientDTO.getCommandeClient()));
            ligneCommandeClient.setQuantite(ligneCommandeClientDTO.getQuantite());
            ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDTO.getPrixUnitaire());
            return ligneCommandeClient;
        }

    }
}
