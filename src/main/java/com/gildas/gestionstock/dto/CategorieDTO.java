package com.gildas.gestionstock.dto;

import com.gildas.gestionstock.entity.Categorie;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategorieDTO {
    private Integer id;
    private String code;
    private String description;
    private List<ArticleDTO> articles;

}
