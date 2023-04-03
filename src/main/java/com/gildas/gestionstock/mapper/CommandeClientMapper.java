package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.CommandeClientDTO;
import com.gildas.gestionstock.entity.CommandeClient;
import lombok.Builder;
import lombok.Data;
import java.util.stream.Collectors;

@Builder
@Data
public class CommandeClientMapper {

    public static CommandeClientDTO toDTO(CommandeClient commandeClients) {
        if (commandeClients==null){
            return  null;
        }
        else {
            return CommandeClientDTO.builder()
                    .id(commandeClients.getId())
                    .code(commandeClients.getCode())
                    .etat(commandeClients.getEtat())
                    .dateCommande(commandeClients.getDateCommande())
                    .ligneCommandeClients(commandeClients.getLigneCommandeClients().stream().map(LigneCommandeClientMapper::toDTO).collect(Collectors.toList()))
                    .build();
        }
    }

    public static CommandeClient toEntity(CommandeClientDTO commandeClientDTO) {
        if (commandeClientDTO==null){
            return  null;
        }
        else {
            CommandeClient commandeClient = new CommandeClient();
            commandeClient.setId(commandeClientDTO.getId());
            commandeClient.setCode(commandeClientDTO.getCode());
            commandeClient.setEtat(commandeClientDTO.getEtat());
            commandeClient.setDateCommande(commandeClientDTO.getDateCommande());
            commandeClient.setLigneCommandeClients(commandeClientDTO.getLigneCommandeClients().stream().map(LigneCommandeClientMapper::toEntity).collect(Collectors.toList()));
            return commandeClient;
        }

    }
}
