package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.LigneCommandeClient;
import com.gildas.gestionstock.entity.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idcommande) ;

    List<LigneCommandeFournisseur> findByCommandeFournisseurId(Integer id);
    List<LigneCommandeFournisseur> findAllByArticleId(Integer idarticle);
}
