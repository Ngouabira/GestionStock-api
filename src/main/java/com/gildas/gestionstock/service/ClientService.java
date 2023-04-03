package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO save(ClientDTO dto);
    ClientDTO findById(Integer id);
    List<ClientDTO> findAll();
    void delete(Integer id);
}
