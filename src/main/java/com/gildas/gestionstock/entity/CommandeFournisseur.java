package com.gildas.gestionstock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class CommandeFournisseur extends AbstractEntity {

    private String code;
    private EtatCommande etat;
    @Column(name = "datecommande")
    private Instant dateCommande;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

   @JsonIgnore
    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}
