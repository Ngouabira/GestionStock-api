package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.FournisseurDTO;
import com.gildas.gestionstock.entity.Fournisseur;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class FournisseurMapper {
    List<CommandeFournisseurMapper> commandeFournisseurs;

    public static FournisseurDTO toDTO(Fournisseur fournisseur){

        if(fournisseur==null){
            return null;
        }
        else {
            return FournisseurDTO.builder()
                    .id(fournisseur.getId())
                    .nom(fournisseur.getNom())
                    .prenom(fournisseur.getPrenom())
                    .email(fournisseur.getEmail())
                    .telephone(fournisseur.getTelephone())
                    .genre(fournisseur.getGenre())
                    .photo(fournisseur.getPhoto())
                    .adresse(AdresseMapper.toDTO(fournisseur.getAdresse()))
                    .commandeFournisseurs(fournisseur.getCommandeFournisseurs().stream().map(CommandeFournisseurMapper::toDTO).collect(Collectors.toList()))
                    .build();
        }
    }

    public static Fournisseur toEntity(FournisseurDTO fournisseurDTO){

        if(fournisseurDTO==null){
            return  null;
        }
        else {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setId(fournisseurDTO.getId());
            fournisseur.setNom(fournisseurDTO.getNom());
            fournisseur.setPrenom(fournisseurDTO.getPrenom());
            fournisseur.setEmail(fournisseurDTO.getEmail());
            fournisseur.setTelephone(fournisseurDTO.getTelephone());
            fournisseur.setGenre(fournisseurDTO.getGenre());
            fournisseur.setPhoto(fournisseurDTO.getPhoto());
            fournisseur.setAdresse(AdresseMapper.toEntity(fournisseurDTO.getAdresse()));
            fournisseur.setCommandeFournisseurs(fournisseurDTO.getCommandeFournisseurs().stream().map(CommandeFournisseurMapper::toEntity).collect(Collectors.toList()));
            return fournisseur;
        }
    }
}
