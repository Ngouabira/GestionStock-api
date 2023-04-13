package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {
    Optional<CommandeClient> findByCode(String code);

    List<CommandeClient> findByClientId(Integer id);
}
