package com.gildas.gestionstock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.dto.FournisseurDTO;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.service.FlickrService;
import com.gildas.gestionstock.service.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("FournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDTO> {
    private FlickrService flickrService;
    private FournisseurService fournisseurService;
    @Override
    public FournisseurDTO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDTO fournisseur = fournisseurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseur.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseur);
    }
}
