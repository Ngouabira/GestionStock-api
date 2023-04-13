package com.gildas.gestionstock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.awt.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class Categorie extends  AbstractEntity {

    private String code;
    private String description;

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles;
}
