package com.gildas.gestionstock.controller;


import com.flickr4java.flickr.FlickrException;
import com.gildas.gestionstock.service.strategy.StrategyPhotoContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/photo")
@AllArgsConstructor
public class PhotoController {


    StrategyPhotoContext strategyPhotoContext;

    @PostMapping("{id}/{context}/{titre}")
    Object savePhoto(@PathVariable("context") String context, @PathVariable("id") Integer id, @RequestPart("file") MultipartFile photo, @PathVariable("titre") String titre) throws FlickrException, IOException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), titre);
    }
}
