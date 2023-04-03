package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MouvemetStockRepository extends JpaRepository<MouvementStock, Integer> {

    @Query("select sum(m.quantite) from MouvementStock m where m.article.id = :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);

    List<MouvementStock> findAllByArticleId(@Param("idArticle") Integer idArticle);
}
