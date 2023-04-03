package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Optional<Article> findByCode(String code);
    List<Article> findAllByCategorieId(Integer idcategorie);
}
