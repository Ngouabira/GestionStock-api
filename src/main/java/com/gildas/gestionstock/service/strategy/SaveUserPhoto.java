package com.gildas.gestionstock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.dto.UserDTO;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.service.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("UserStrategy")
@Slf4j
public class SaveUserPhoto implements Strategy<UserDTO> {
    private FlickrService flickrService;
    //private UserService userService;
    @Override
    public UserDTO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
       /* UserDTO user = userService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        user.setPhoto(urlPhoto);
        return userService.save(user);*/
        return null;
    }
}
