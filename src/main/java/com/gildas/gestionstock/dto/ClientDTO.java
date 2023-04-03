package com.gildas.gestionstock.dto;

import com.gildas.gestionstock.entity.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ClientDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String genre;
    private String photo;
    private AdresseDTO adresse;

    List<CommandeClientDTO> commandeClients;
}
