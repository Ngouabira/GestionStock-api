package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.CategorieDTO;
import com.gildas.gestionstock.entity.Categorie;

import java.util.function.Function;

public class ExampleMapper implements Function<Categorie, CategorieDTO> {
    @Override
    public CategorieDTO apply(Categorie categorie) {
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
}
