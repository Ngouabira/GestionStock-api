package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.CategorieDTO;
import com.gildas.gestionstock.service.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categorie")
@AllArgsConstructor
public class CategorieController {

    CategorieService categorieService;

    @GetMapping
    public List<CategorieDTO> findAll(){
        return categorieService.findAll();
    }

    @PostMapping
    public CategorieDTO save(@RequestBody CategorieDTO dto){
       return categorieService.save(dto);
    }

    @GetMapping("/{id}")
    public CategorieDTO findById(@PathVariable("id") Integer id){
        return categorieService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
         categorieService.delete(id);
    }
}
