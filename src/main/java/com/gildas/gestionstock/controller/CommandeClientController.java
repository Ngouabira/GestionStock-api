package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.CommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.entity.EtatCommande;
import com.gildas.gestionstock.service.CommandeClientService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/commandeclient")
public class CommandeClientController {

    CommandeClientService commandeClientService;

    @GetMapping
    public List<CommandeClientDTO> findAll(){
        return commandeClientService.findAll();
    }

    @PostMapping
    public CommandeClientDTO save(@RequestBody CommandeClientDTO dto){
        return commandeClientService.save(dto);
    }

    @PutMapping("update-etat/{id}")
    public CommandeClientDTO updateEtat(@PathVariable("id") Integer id, @RequestParam("etat") EtatCommande etat){
        return commandeClientService.updateEtat(id, etat);
    }

    @PatchMapping("update-fournisseur/{idcommande}/{idfournisseur}")
    public CommandeClientDTO updateClient(@PathVariable("idcommande") Integer id,@PathVariable("idfournisseur") Integer idfournisseur){
        return commandeClientService.updateClient(id, idfournisseur);
    }
    @PatchMapping("update-article/{idcommande}/{idlignecommande}/{idarticle}")
    public CommandeClientDTO updateArticle(@PathVariable("idcommande") Integer id,@PathVariable("idlignecommande") Integer idlignecommande, @PathVariable("idarticle") Integer idarticle){
        return commandeClientService.updateArticle(id, idlignecommande, idarticle);
    }

    @PatchMapping("update-quantite/{idcommande}/{idlignecommande}")
    public CommandeClientDTO updateQuantite(@PathVariable("id") Integer id,@PathVariable("idlignecommande") Integer idlignecommande, @RequestParam("quantite") BigDecimal quantite){
        return commandeClientService.updateQuantite(id, idlignecommande, quantite);
    }

    @GetMapping("find-all-lignecommande-by-commande-id/{id}")
    public List<LigneCommandeClientDTO> findAllLigneCommandeByCommandeId(@PathVariable("id") Integer id){
        return commandeClientService.findAllLigneCommandeByCommandeId(id);
    }

    @PatchMapping("delete-article/{idcommande}/{idlignecommande}")
    public CommandeClientDTO deleteArticle(@PathVariable("idcommande") Integer id, @PathVariable("idlignecommande") Integer idlignecommande){
        return commandeClientService.deleteArticle(id, idlignecommande);
    }

    @GetMapping("/{id}")
    public CommandeClientDTO findById(@PathVariable("id") Integer id){
        return commandeClientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        commandeClientService.delete(id);
    }

    @GetMapping("findby-code/{code}")
    public CommandeClientDTO findByCode(@PathVariable("id") String code){
        return commandeClientService.findByCode(code);
    }
}
