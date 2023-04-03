package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.CommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.entity.*;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.*;
import com.gildas.gestionstock.repository.ArticleRepository;
import com.gildas.gestionstock.repository.ClientRepository;
import com.gildas.gestionstock.repository.CommandeClientRepository;
import com.gildas.gestionstock.repository.LigneCommandeClientRepository;
import com.gildas.gestionstock.service.CommandeClientService;
import com.gildas.gestionstock.service.MouvementStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    CommandeClientRepository commandeClientRepository;
    ClientRepository clientRepository;
    ArticleRepository articleRepository;
    LigneCommandeClientRepository LigneCommandeClientRepository;

    MouvementStockService mouvementStockService;

    @Override
    public CommandeClientDTO save(CommandeClientDTO dto) {

        if (dto.getId()!=null && isCommandeLivree(dto)){

            throw new InvalidOperationException("Can not edit a delevered command with id "+dto.getId(), ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (!client.isPresent()){
            log.warn("Client with id "+dto.getClient().getId()+" was not found");
            throw new EntityNotFoundException("Client with id "+dto.getClient().getId()+" was not found", ErrorCodes.CLIENT_NOT_FOUND);
        }
       if ( dto.getLigneCommandeClients() !=null){
           dto.getLigneCommandeClients().forEach(ligne -> {
               if (ligne.getArticle() != null){
                   Optional<Article> article = articleRepository.findById(ligne.getArticle().getId());
                   if (article.isEmpty()){
                       log.warn("Article with id "+ligne.getArticle().getId()+" was not found");
                       throw new EntityNotFoundException("Article with id "+ligne.getArticle().getId()+" was not found", ErrorCodes.CLIENT_NOT_FOUND);
                   }
               }
           });

       }
        CommandeClient commande = commandeClientRepository.save(CommandeClientMapper.toEntity(dto));
        dto.getLigneCommandeClients().forEach(ligne -> {

            LigneCommandeClient LigneCommandeClient = LigneCommandeClientMapper.toEntity(ligne);
            LigneCommandeClient.setCommandeClient(commande);
            LigneCommandeClientRepository.save(LigneCommandeClient);
        });

        return CommandeClientMapper.toDTO(commande);
    }

    //


    @Override
    public CommandeClientDTO updateEtat(Integer idcommande, EtatCommande etatCommande) {
        if (idcommande==null){
            log.error("commandeClient id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("commandeClient etat is null");
            throw new InvalidOperationException("Can not update a command with null etat ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        CommandeClientDTO commandeClient = findById(idcommande);
        if (isCommandeLivree(commandeClient)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeClient.getId(), ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        commandeClient.setEtat(etatCommande);
        CommandeClientDTO savedCommandeClient = CommandeClientMapper.toDTO(commandeClientRepository.save(CommandeClientMapper.toEntity(commandeClient)));
        if (etatCommande==EtatCommande.LIVREE){
            updateMvtStk(idcommande);
        }
        return  savedCommandeClient;
    }

    @Override
    public CommandeClientDTO updateQuantite(Integer idcommande, Integer idlignecommande, BigDecimal quantite) {

        if (idcommande==null){
            log.error("commandeClient id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("commandeClient idlignecommande is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (quantite==null || quantite.compareTo(BigDecimal.ZERO)==0){
            throw new InvalidOperationException("Can not edit a  command with quantite null  or zero", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        CommandeClientDTO commandeClient = findById(idcommande);
        if (isCommandeLivree(commandeClient)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeClient.getId(), ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        Optional<LigneCommandeClient> LigneCommandeClientOptional = LigneCommandeClientRepository.findById(idlignecommande);
        if (LigneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("No LigneCommandeClient with id "+idlignecommande+" was not found", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        LigneCommandeClient LigneCommandeClient = LigneCommandeClientOptional.get();
        LigneCommandeClient.setQuantite(quantite);
        LigneCommandeClientRepository.save(LigneCommandeClient);
        return commandeClient;
    }

    @Override
    public CommandeClientDTO updateClient(Integer idcommande, Integer idclient) {

        if (idcommande==null){
            log.error("commandeClient id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (idclient==null){
            log.error("Client idclient is null");
            throw new InvalidOperationException("Can not update a command with null idclient ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        CommandeClientDTO commandeClient = findById(idcommande);
        if (isCommandeLivree(commandeClient)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeClient.getId(), ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        Optional<Client> ClientOptional = clientRepository.findById(idclient);
        if (ClientOptional.isEmpty()){
            throw new EntityNotFoundException("No supplier with id "+idclient);
        }
        commandeClient.setClient(ClientMapper.toDTO(ClientOptional.get()));
        return CommandeClientMapper.toDTO(commandeClientRepository.save(CommandeClientMapper.toEntity(commandeClient)));
    }

    @Override
    public CommandeClientDTO updateArticle(Integer idcommande, Integer idlignecommande, Integer idarticle) {

        if (idcommande==null){
            log.error("commandeClient id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("Lignecommande id is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (idarticle==null){
            log.error("Newidarticle id is null");
            throw new InvalidOperationException("Can not update a command with null newidarticle ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        CommandeClientDTO commandeClient = findById(idcommande);
        if (isCommandeLivree(commandeClient)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeClient.getId(), ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        Optional<LigneCommandeClient> LigneCommandeClientOptional = LigneCommandeClientRepository.findById(idlignecommande);
        if (LigneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("No LigneCommandeClient with id "+idlignecommande+" was not found", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        Optional<Article> articleOptional = articleRepository.findById(idarticle);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException("Article with id "+idarticle+" was not found", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        LigneCommandeClient LigneCommandeClient = LigneCommandeClientOptional.get();
        Article article = articleOptional.get();

        LigneCommandeClient.setArticle(article);
        LigneCommandeClientRepository.save(LigneCommandeClient);
        return commandeClient;
    }

    @Override
    public CommandeClientDTO deleteArticle(Integer idcommande, Integer idlignecommande) {

        if (idcommande==null){
            log.error("commandeClient id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("Lignecommande id is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }

        CommandeClientDTO commandeClient = findById(idcommande);
        if (isCommandeLivree(commandeClient)){
            throw new InvalidOperationException("Can not delete a lignecommande in  delevered command with id "+idlignecommande, ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        Optional<LigneCommandeClient> LigneCommandeClientOptional = LigneCommandeClientRepository.findById(idlignecommande);
        if (LigneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("No LigneCommandeClient with id "+idlignecommande+" was not found", ErrorCodes.COMMANDE_CLIENT_NOT_EDITABLE);
        }
        LigneCommandeClientRepository.deleteById(idlignecommande);
        return commandeClient;
    }

    @Override
    public List<LigneCommandeClientDTO> findAllLigneCommandeByCommandeId(Integer id) {
        return LigneCommandeClientRepository.findBycommandeClientId(id).stream()
                .map(LigneCommandeClientMapper::toDTO).collect(Collectors.toList());
    }

    private boolean isCommandeLivree(CommandeClientDTO dto){
        return EtatCommande.LIVREE.equals(dto.getEtat());
    }


    //

    @Override
    public CommandeClientDTO findById(Integer id) {

        if (id==null){
            log.error("CommandeClient id not found");
        }

        Optional<CommandeClient> commandeClient = commandeClientRepository.findById(id);
        return Optional.of(CommandeClientMapper.toDTO(commandeClient.get())).orElseThrow(()-> new EntityNotFoundException("La commande "+id+" n'existe pas", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDTO findByCode(String code) {

        if (code==null){
            log.error("CommandeClient code not found");
        }

        Optional<CommandeClient> commandeClient = commandeClientRepository.findByCode(code);
        return Optional.of(CommandeClientMapper.toDTO(commandeClient.get())).orElseThrow(()-> new EntityNotFoundException("La commande avec le code "+code+" n'existe pas", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDTO> findAll() {
        return commandeClientRepository.findAll().stream().map(CommandeClientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("CommandeClient id not found");
        }

        List<LigneCommandeClient> ligneCommandeClients = LigneCommandeClientRepository.findAllByCommandeClientId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer une commande client déjà utilisée dans les lignecommandes client", ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }

        commandeClientRepository.deleteById(id);
    }

    private void updateMvtStk(Integer idcommande){
        List<LigneCommandeClient> ligneCommandeClients = LigneCommandeClientRepository.findAllByCommandeClientId(idcommande);
        ligneCommandeClients.forEach(ligne -> {
            MouvementStockDTO dto = MouvementStockDTO.builder()
            .article(ArticleMapper.toDTO(ligne.getArticle()))
                    .datemvt(Instant.now())
                    .typemvt(TypeMouvementStock.SORTIE)
                    .sourcemvt(SourceMvtStk.COMMANDE_CLIENT)
                    .quantite(ligne.getQuantite())
                    .build();
            mouvementStockService.sortieStock(dto);
        });
    }
}
