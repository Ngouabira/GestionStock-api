package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.CommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.entity.EtatCommande;
import com.gildas.gestionstock.service.CommandeFournisseurService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/commandefournisseur")
public class CommandeFournisseurController {

    CommandeFournisseurService commandeFournisseurService;

    @GetMapping
    public List<CommandeFournisseurDTO> findAll(){
        return commandeFournisseurService.findAll();
    }

    @PostMapping
    public CommandeFournisseurDTO save(@RequestBody CommandeFournisseurDTO dto){
        return commandeFournisseurService.save(dto);
    }

    @PatchMapping("update-fournisseur/{idcommande}/{idfournisseur}")
    public CommandeFournisseurDTO updateFournisseur(@PathVariable("idcommande") Integer id,@PathVariable("idfournisseur") Integer idfournisseur){
        return commandeFournisseurService.updateFournisseur(id, idfournisseur);
    }
    @PatchMapping("update-article/{idcommande}/{idlignecommande}/{idarticle}")
    public CommandeFournisseurDTO updateArticle(@PathVariable("idcommande") Integer id,@PathVariable("idlignecommande") Integer idlignecommande, @PathVariable("idarticle") Integer idarticle){
        return commandeFournisseurService.updateArticle(id, idlignecommande, idarticle);
    }
    @PutMapping("update-etat/{id}")
    public CommandeFournisseurDTO updateEtat(@PathVariable("id") Integer id,@RequestParam("etat") EtatCommande etat){
        return commandeFournisseurService.updateEtat(id, etat);
    }

    @PatchMapping("update-quantite/{idcommande}/{idlignecommande}")
    public CommandeFournisseurDTO updateQuantite(@PathVariable("id") Integer id,@PathVariable("idlignecommande") Integer idlignecommande, @RequestParam("quantite") BigDecimal quantite){
        return commandeFournisseurService.updateQuantite(id, idlignecommande, quantite);
    }

    @GetMapping("/{id}")
    public List<LigneCommandeFournisseurDTO> findAllLigneCommandeByCommandeId(@PathVariable("id") Integer id){
        return commandeFournisseurService.findAllLigneCommandeByCommandeId(id);
    }

    @GetMapping("/{id}")
    public CommandeFournisseurDTO findById(@PathVariable("id") Integer id){
        return commandeFournisseurService.findById(id);
    }

    @PatchMapping("delete-article/{idcommande}/{idlignecommande}")
    public CommandeFournisseurDTO deleteArticle(@PathVariable("idcommande") Integer id,@PathVariable("idlignecommande") Integer idlignecommande){
        return commandeFournisseurService.deleteArticle(id, idlignecommande);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        commandeFournisseurService.delete(id);
    }

    @GetMapping("findby-code/{code}")
    public CommandeFournisseurDTO findByCode(@PathVariable("id") String code){
        return commandeFournisseurService.findByCode(code);
    }
}
