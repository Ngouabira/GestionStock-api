package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.CommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.entity.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDTO save(CommandeClientDTO dto);
    CommandeClientDTO findById(Integer id);
    CommandeClientDTO updateEtat(Integer idcommande, EtatCommande etatCommande);
    CommandeClientDTO updateQuantite(Integer idcommande, Integer idlignecommande, BigDecimal quantite);
    CommandeClientDTO updateClient(Integer idcommande, Integer idclient);
    CommandeClientDTO updateArticle(Integer idcommande, Integer idlignecommande, Integer newidarticle);
    //Delete article  => delete lignecommandeClient
    CommandeClientDTO deleteArticle(Integer idcommande, Integer idlignecommande);
    List<LigneCommandeClientDTO> findAllLigneCommandeByCommandeId(Integer id);
    CommandeClientDTO findByCode(String code);
    List<CommandeClientDTO> findAll();
    void delete(Integer id);
}
