package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.ClientDTO;
import com.gildas.gestionstock.entity.Client;
import com.gildas.gestionstock.entity.CommandeClient;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.ClientMapper;
import com.gildas.gestionstock.repository.ClientRepository;
import com.gildas.gestionstock.repository.CommandeClientRepository;
import com.gildas.gestionstock.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;
    CommandeClientRepository commandeClientRepository;

    @Override
    public ClientDTO save(ClientDTO dto) {
        return ClientMapper.toDTO(clientRepository.save(ClientMapper.toEntity(dto))) ;
    }

    @Override
    public ClientDTO findById(Integer id) {
        if (id==null){
            log.error("Id du client est nul");
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientMapper.toDTO(client.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Le client avec l'id "+id+" n'existe pas",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(ClientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Id est nul");
            return;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByCientId(id);
        if (!commandeClients.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer un client déjà utilisé dans les commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }
}
