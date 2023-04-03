package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.VenteDTO;

import java.util.List;

public interface VenteService {

    VenteDTO save(VenteDTO dto);
    VenteDTO findById(Integer id);
    VenteDTO findByCode(String code);
    List<VenteDTO> findAll();
    void delete(Integer id);
}
