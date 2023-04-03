package com.gildas.gestionstock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.dto.ClientDTO;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.service.ClientService;
import com.gildas.gestionstock.service.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("ClientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDTO> {
    private FlickrService flickrService;
    private ClientService clientService;
    @Override
    public ClientDTO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ClientDTO client = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        client.setPhoto(urlPhoto);
        return clientService.save(client);
    }
}
