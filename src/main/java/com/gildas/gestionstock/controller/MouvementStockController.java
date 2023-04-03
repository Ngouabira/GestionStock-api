package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.service.MouvementStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/mouvementstock")
@AllArgsConstructor
public class MouvementStockController {

    MouvementStockService mouvementStockService;

    @GetMapping("stock-reel-article/{id}")
    public BigDecimal stockReelArticle(@PathVariable("id") Integer id){
        return mouvementStockService.stockReelArticle(id);
    }

    @GetMapping("mouvement-stock-article/{id}")
    public List<MouvementStockDTO> mvtStkArticle(@PathVariable("id") Integer id){
        return mouvementStockService.mvtStkArticle(id);
    }

    @PostMapping("entree-stock")
    public MouvementStockDTO entreeStock(@RequestBody MouvementStockDTO dto){
        return mouvementStockService.entreeStock(dto);
    }

    @PostMapping("sortie-stock")
    public MouvementStockDTO sortieStock(@RequestBody MouvementStockDTO dto){
        return mouvementStockService.sortieStock(dto);
    }

    @PostMapping("correction-stock-pos")
    public MouvementStockDTO correctionStockPos(@RequestBody MouvementStockDTO dto){
        return mouvementStockService.correctionStockPos(dto);
    }

    @PostMapping("correction-stock-neg")
    public MouvementStockDTO correctionStockNeg(@RequestBody MouvementStockDTO dto){
        return mouvementStockService.correctionStockNeg(dto);
    }

    @GetMapping
    public List<MouvementStockDTO> findAll(){
        return mouvementStockService.findAll();
    }

    @PostMapping
    public MouvementStockDTO save(@RequestBody MouvementStockDTO dto){
        return mouvementStockService.save(dto);
    }

    @GetMapping("/{id}")
    public MouvementStockDTO findById(@PathVariable("id") Integer id){
        return mouvementStockService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        mouvementStockService.delete(id);
    }
}
