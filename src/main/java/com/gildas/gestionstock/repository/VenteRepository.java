package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenteRepository extends JpaRepository<Vente, Integer> {
    Optional<Vente> findByCode(String code);
}
