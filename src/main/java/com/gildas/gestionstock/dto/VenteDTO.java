package com.gildas.gestionstock.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class VenteDTO {

    private Integer id;
    private String code;
    private Instant dateVente;
    private String commentaire;

    List<LigneVenteDTO> ligneVentes;
}
