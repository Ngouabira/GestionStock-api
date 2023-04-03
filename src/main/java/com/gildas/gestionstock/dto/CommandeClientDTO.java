package com.gildas.gestionstock.dto;

import com.gildas.gestionstock.entity.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeClientDTO {
    private Integer id;
    private String code;
    private EtatCommande etat;
    private Instant dateCommande;
    private ClientDTO client;

    List<LigneCommandeClientDTO> ligneCommandeClients;

}
