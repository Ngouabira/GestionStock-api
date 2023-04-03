package com.gildas.gestionstock.service;

import com.gildas.gestionstock.dto.MouvementStockDTO;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementStockService {

    MouvementStockDTO save(MouvementStockDTO dto);
    MouvementStockDTO findById(Integer id);
    List<MouvementStockDTO> findAll();
    void delete(Integer id);

    BigDecimal stockReelArticle(Integer idArticle);
    List<MouvementStockDTO> mvtStkArticle(Integer idArticle);
    MouvementStockDTO entreeStock(MouvementStockDTO dto);
    MouvementStockDTO sortieStock(MouvementStockDTO dto);
    MouvementStockDTO correctionStockPos(MouvementStockDTO dto);
    MouvementStockDTO correctionStockNeg(MouvementStockDTO dto);
}
