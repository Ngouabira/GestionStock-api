package com.gildas.gestionstock.auth.service;

import com.gildas.gestionstock.auth.entity.Token;
import com.gildas.gestionstock.auth.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void save(Token token) {
        tokenRepository.save(token);
    }

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token findByUserUsername(String username) {
        return tokenRepository.findByUserUsername(username);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }
}
