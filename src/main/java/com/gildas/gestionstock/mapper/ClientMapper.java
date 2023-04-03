package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.ClientDTO;
import com.gildas.gestionstock.entity.Client;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Builder
@Data
public class ClientMapper {

    public static ClientDTO toDTO(Client client){

        if(client==null){
            return null;
        }
        else {
            return ClientDTO.builder()
                    .id(client.getId())
                    .nom(client.getNom())
                    .prenom(client.getPrenom())
                    .email(client.getEmail())
                    .telephone(client.getTelephone())
                    .genre(client.getGenre())
                    .photo(client.getPhoto())
                    .adresse(AdresseMapper.toDTO(client.getAdresse()))
                    .commandeClients(client.getCommandeClients().stream().map(CommandeClientMapper::toDTO).collect(Collectors.toList()))
                    .build();
        }
    }

    public static Client toEntity(ClientDTO clientDTO){

        if(clientDTO==null){
            return  null;
        }
        else {
            Client client = new Client();
            client.setId(clientDTO.getId());
            client.setNom(clientDTO.getNom());
            client.setPrenom(clientDTO.getPrenom());
            client.setEmail(clientDTO.getEmail());
            client.setTelephone(client.getTelephone());
            client.setGenre(clientDTO.getGenre());
            client.setPhoto(clientDTO.getPhoto());
            client.setAdresse(AdresseMapper.toEntity(clientDTO.getAdresse()));
            client.setCommandeClients(clientDTO.getCommandeClients().stream().map(CommandeClientMapper::toEntity).collect(Collectors.toList()));
            return client;
        }
    }
}
