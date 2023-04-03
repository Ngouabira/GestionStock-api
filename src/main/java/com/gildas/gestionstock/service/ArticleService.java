package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.ArticleDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneVenteDTO;

import java.util.List;

public interface ArticleService {

    ArticleDTO save(ArticleDTO dto);
    ArticleDTO findById(Integer id);
    ArticleDTO findByCodeArticle(String code);
    List<ArticleDTO> findAll();
    List<LigneVenteDTO> findHistoriqueVente(Integer idarticle);
    List<LigneCommandeClientDTO> findHistoriqueCommandeClient(Integer idarticle);
    List<LigneCommandeFournisseurDTO>  findHistoriqueCommandeFournisseur(Integer idarticle);
    List<ArticleDTO> findAllByIdCategorie(Integer idcategorie);
    void delete(Integer id);
}
