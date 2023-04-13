package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.FournisseurDTO;
import com.gildas.gestionstock.service.FournisseurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/fournisseur")
public class FournisseurController {

    FournisseurService fournisseurService;

    @GetMapping
    public List<FournisseurDTO> findAll(){
        return fournisseurService.findAll();
    }

    @PostMapping
    public FournisseurDTO save(@RequestBody FournisseurDTO dto){
        return fournisseurService.save(dto);
    }

    @GetMapping("/{id}")
    public FournisseurDTO findById(@PathVariable("id") Integer id){
        return fournisseurService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        fournisseurService.delete(id);
    }
}
