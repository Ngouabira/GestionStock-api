package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.ArticleDTO;
import com.gildas.gestionstock.entity.Article;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ArticleMapper {

    public static ArticleDTO toDTO(Article article){

        if (article==null){
            return null;
        }
        else {
            return ArticleDTO.builder()
                    .id(article.getId())
                    .code(article.getCode())
                    .description(article.getDescription())
                    .prixUnitaireHt(article.getPrixUnitaireHt())
                    .tauxTva(article.getTauxTva())
                    .prixUnitaireTtc(article.getPrixUnitaireTtc())
                    .photo(article.getPhoto())
                    .categorie(CategorieMapper.toDTO(article.getCategorie()))
                    .build();
        }
    }

    public static Article toEntity(ArticleDTO articleDTO){

        if (articleDTO==null){
            return null;
        }
        else {
            Article article = new Article();
            article.setId(articleDTO.getId());
            article.setCode(articleDTO.getCode());
            article.setDescription(articleDTO.getDescription());
            article.setPrixUnitaireHt(articleDTO.getPrixUnitaireHt());
            article.setTauxTva(articleDTO.getTauxTva());
            article.setPhoto(articleDTO.getPhoto());
            article.setCategorie(CategorieMapper.toEntity(articleDTO.getCategorie()));
            return article;
        }
    }
}
