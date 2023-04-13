package com.gildas.gestionstock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class CommandeClient extends AbstractEntity {

    private String code;
    private EtatCommande etat;
    @Column(name = "datecommande")
    private Instant dateCommande;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

   @JsonIgnore
    @OneToMany(mappedBy = "commandeClient")
    List<LigneCommandeClient> ligneCommandeClients;
}
