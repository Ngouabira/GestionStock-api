package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {
    List<LigneCommandeClient> findByCommandeClientId(Integer id);

    List<LigneCommandeClient> findAllByArticleId(Integer idarticle);

    List<LigneCommandeClient> findAllByCommandeClientId(Integer idcommande);
}
