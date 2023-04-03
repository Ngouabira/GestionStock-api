package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.ArticleDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneVenteDTO;
import com.gildas.gestionstock.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/article")
@AllArgsConstructor
public class ArticleController {

    ArticleService articleService;
    @GetMapping
    public List<ArticleDTO> findAll(@PathVariable("id") int id){
        return articleService.findAll();
    }
    @PostMapping
    public ArticleDTO save(@RequestBody ArticleDTO dto){
        return articleService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
         articleService.delete(id);
    }

    @GetMapping("/{id}")
    public ArticleDTO findById(@PathVariable("id") int id){
        return articleService.findById(id);
    }

    @GetMapping("findby-code/{code}")
    public ArticleDTO findByCode(@PathVariable("id") String code){
        return articleService.findByCodeArticle(code);
    }

    @GetMapping("find-historique-vente/{id}")
    List<LigneVenteDTO> findHistoriqueVente(@PathVariable("id") Integer id){
        return articleService.findHistoriqueVente(id);
    }
    @GetMapping("find-historique-commande-client/{code}")
    List<LigneCommandeClientDTO> findHistoriqueCommandeClient(@PathVariable("id") Integer id){
        return articleService.findHistoriqueCommandeClient(id);
    }
    @GetMapping("find-historique-commande-fournisseur/{id}")
    List<LigneCommandeFournisseurDTO>  findHistoriqueCommandeFournisseur(@PathVariable("id") Integer id){
        return articleService.findHistoriqueCommandeFournisseur(id);
    }
    @GetMapping("find-all-by-id-categorie/{id}")
    List<ArticleDTO> findAllByIdCategorie(@PathVariable("id") Integer id){
        return articleService.findAllByIdCategorie(id);
    }


}
