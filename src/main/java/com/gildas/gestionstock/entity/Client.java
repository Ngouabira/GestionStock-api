package com.gildas.gestionstock.entity;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class Client extends AbstractEntity {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String genre;
    private String photo;

    //ce champ est un champ composé
    @Embedded
    private Adresse adresse;

    @OneToMany(mappedBy = "client")
    List<CommandeClient> commandeClients;

}
