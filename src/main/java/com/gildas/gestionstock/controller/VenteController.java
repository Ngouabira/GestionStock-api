package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.VenteDTO;
import com.gildas.gestionstock.service.VenteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vente")
@AllArgsConstructor
public class VenteController {

    VenteService venteService;

    @GetMapping
    public List<VenteDTO> findAll(){
        return venteService.findAll();
    }

    @PostMapping
    public VenteDTO save(@RequestBody VenteDTO dto){
        return venteService.save(dto);
    }

    @GetMapping("/{id}")
    public VenteDTO findById(@PathVariable("id") Integer id){
        return venteService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        venteService.delete(id);
    }

    @GetMapping("findby-code/{code}")
    public VenteDTO findByCode(@PathVariable("id") String code){
        return venteService.findByCode(code);
    }
}
