package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.CategorieDTO;

import java.util.List;

public interface CategorieService {

    CategorieDTO save(CategorieDTO dto);
    CategorieDTO findById(Integer id);
    List<CategorieDTO> findAll();
    void delete(Integer id);
}
