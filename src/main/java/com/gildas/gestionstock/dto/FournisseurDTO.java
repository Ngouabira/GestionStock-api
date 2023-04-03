package com.gildas.gestionstock.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FournisseurDTO {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String genre;
    private String photo;

    private AdresseDTO adresse;

    List<CommandeFournisseurDTO> commandeFournisseurs;
}
