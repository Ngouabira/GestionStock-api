package com.gildas.gestionstock.service.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {

    @Autowired
    private BeanFactory beanFactory;

    @Setter
    private Strategy strategy;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory){ this.beanFactory=beanFactory;}

    public Object savePhoto(String context, Integer id, InputStream photo, String titre) throws FlickrException {
        determineContext(context);
        return strategy.savePhoto(id, photo, titre);
    }
    private void determineContext(String context){

        final String beanName = context +"Strategy";
        switch (context){
            case "Article":
                beanFactory.getBean(beanName,SaveArticlePhoto.class);
                break;
            case "User":
                beanFactory.getBean(beanName,SaveUserPhoto.class);
                break;
            case "Client":
                beanFactory.getBean(beanName,SaveClientPhoto.class);
                break;
            case "Fournisseur":
                beanFactory.getBean(beanName,SaveFournisseurPhoto.class);
                break;
            default : throw new InvalidOperationException("Opération inconnue", ErrorCodes.UNKNOW_CONTEXT);
        }
    }

}
