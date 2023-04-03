package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.CategorieDTO;
import com.gildas.gestionstock.entity.Categorie;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CategorieMapper {

    public static CategorieDTO toDTO(Categorie categorie) {

        if (categorie == null) {

            return null;
        } else {
            return CategorieDTO.builder()
                    .id(categorie.getId())
                    .code(categorie.getCode())
                    .description(categorie.getDescription())
                    .build();
        }
    }


    public static Categorie toEntity(CategorieDTO categorieDTO) {

        if (categorieDTO == null) {

            return null;
        } else {
           Categorie categorie = new Categorie();
            categorie.setId(categorieDTO.getId());
            categorie.setCode(categorieDTO.getCode());
            categorie.setDescription(categorieDTO.getDescription());
            return categorie;
        }
    }

}
