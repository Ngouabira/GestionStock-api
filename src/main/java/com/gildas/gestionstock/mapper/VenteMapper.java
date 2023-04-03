package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.VenteDTO;
import com.gildas.gestionstock.entity.Vente;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VenteMapper {

    public static VenteDTO toDTO(Vente vente){

        if (vente==null){
            return null;
        }
        else{

            return VenteDTO.builder()
                    .id(vente.getId())
                    .code(vente.getCode())
                    .dateVente(vente.getDateVente())
                    .commentaire(vente.getCommentaire())
                    .build();
        }
    }

    public static Vente toEntity(VenteDTO venteDTO){

        if (venteDTO==null){
            return null;
        }
        else {
            Vente vente = new Vente();
            vente.setId(venteDTO.getId());
            vente.setCode(venteDTO.getCode());
            vente.setDateVente(venteDTO.getDateVente());
            vente.setCommentaire(venteDTO.getCommentaire());
            return vente;
        }
    }
}
