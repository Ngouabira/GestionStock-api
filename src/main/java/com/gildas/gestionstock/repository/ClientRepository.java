package com.gildas.gestionstock.repository;

import com.gildas.gestionstock.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
