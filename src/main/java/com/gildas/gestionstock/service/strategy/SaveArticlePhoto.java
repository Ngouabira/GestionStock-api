package com.gildas.gestionstock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.dto.ArticleDTO;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.service.ArticleService;
import com.gildas.gestionstock.service.FlickrService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("ArticleStrategy")
@Slf4j
@AllArgsConstructor
public abstract class SaveArticlePhoto implements Strategy<ArticleDTO>{

    private FlickrService flickrService;
    private ArticleService articleService;
    @Override
    public ArticleDTO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDTO articleDTO = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDTO.setPhoto(urlPhoto);
        return articleService.save(articleDTO);
    }
}
