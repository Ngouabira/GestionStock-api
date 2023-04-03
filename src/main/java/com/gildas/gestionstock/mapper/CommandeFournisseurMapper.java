package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.CommandeFournisseurDTO;
import com.gildas.gestionstock.entity.CommandeFournisseur;
import lombok.Builder;
import lombok.Data;
import java.util.stream.Collectors;

@Builder
@Data
public class CommandeFournisseurMapper {

    public static CommandeFournisseurDTO toDTO(CommandeFournisseur commandeFournisseur) {
        if (commandeFournisseur==null){
            return  null;
        }
        else {
            return CommandeFournisseurDTO.builder()
                    .id(commandeFournisseur.getId())
                    .code(commandeFournisseur.getCode())
                    .etat(commandeFournisseur.getEtat())
                    .dateCommande(commandeFournisseur.getDateCommande())
                    .ligneCommandeFournisseurs(commandeFournisseur.getLigneCommandeFournisseurs().stream().map(LigneCommandeFournisseurMapper::toDTO).collect(Collectors.toList()))
                    .build();
        }
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDTO CommandeFournisseurDTO) {
        if (CommandeFournisseurDTO==null){
            return  null;
        }
        else {
            CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
            commandeFournisseur.setId(CommandeFournisseurDTO.getId());
            commandeFournisseur.setCode(CommandeFournisseurDTO.getCode());
            commandeFournisseur.setEtat(CommandeFournisseurDTO.getEtat());
            commandeFournisseur.setDateCommande(CommandeFournisseurDTO.getDateCommande());
            commandeFournisseur.setLigneCommandeFournisseurs(CommandeFournisseurDTO.getLigneCommandeFournisseurs().stream().map(LigneCommandeFournisseurMapper::toEntity).collect(Collectors.toList()));
            return commandeFournisseur;
        }

    }
}
