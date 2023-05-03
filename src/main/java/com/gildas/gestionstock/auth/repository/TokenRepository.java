package com.gildas.gestionstock.auth.repository;

import com.gildas.gestionstock.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String token);

    Token findByUserUsername(String username);

}
