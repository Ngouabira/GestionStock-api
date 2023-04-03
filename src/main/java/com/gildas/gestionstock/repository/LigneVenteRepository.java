package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    List<LigneVente> findAllByArticleId(Integer idarticle);

    List<LigneVente> findAllByVenteId(Integer id);
}
