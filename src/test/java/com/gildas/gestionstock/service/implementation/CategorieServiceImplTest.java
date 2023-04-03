package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.CategorieDTO;
import com.gildas.gestionstock.service.CategorieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategorieServiceImplTest {

    @Autowired
    private CategorieService service;


    @Test
    public void shouldSaveCategorieWithSuccess(){

        CategorieDTO exceptedCategorie =  CategorieDTO.builder()
                .code("azerty")
                .description("querty")
                .build();

        CategorieDTO savedCategorie = service.save(exceptedCategorie);

        assertNotNull(savedCategorie);
    }
}