package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.CommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.entity.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDTO save(CommandeFournisseurDTO dto);
    CommandeFournisseurDTO updateEtat(Integer idcommande, EtatCommande etatCommande);
    CommandeFournisseurDTO updateQuantite(Integer idcommande, Integer idlignecommande, BigDecimal quantite);
    CommandeFournisseurDTO updateFournisseur(Integer idcommande, Integer idfournisseur);
    CommandeFournisseurDTO updateArticle(Integer idcommande, Integer idlignecommande, Integer newidarticle);
    //Delete article  => delete lignecommandeFournisseur
    CommandeFournisseurDTO deleteArticle(Integer idcommande, Integer idlignecommande);
    CommandeFournisseurDTO findById(Integer id);
    List<LigneCommandeFournisseurDTO> findAllLigneCommandeByCommandeId(Integer id);
    CommandeFournisseurDTO findByCode(String code);
    List<CommandeFournisseurDTO> findAll();
    void delete(Integer id);
}
