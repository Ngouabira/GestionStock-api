package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
