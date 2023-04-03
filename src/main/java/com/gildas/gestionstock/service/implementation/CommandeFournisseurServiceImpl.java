package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.CommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.entity.*;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.ArticleMapper;
import com.gildas.gestionstock.mapper.CommandeFournisseurMapper;
import com.gildas.gestionstock.mapper.FournisseurMapper;
import com.gildas.gestionstock.mapper.LigneCommandeFournisseurMapper;
import com.gildas.gestionstock.repository.*;
import com.gildas.gestionstock.service.CommandeFournisseurService;
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
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    CommandeFournisseurRepository commandeFournisseurRepository;
    FournisseurRepository fournisseurRepository;
    ArticleRepository articleRepository;
    LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    MouvementStockService mouvementStockService;

    @Override
    public CommandeFournisseurDTO save(CommandeFournisseurDTO dto) throws InvalidOperationException {

        if (dto.getId()!=null && isCommandeLivree(dto)){

            throw new InvalidOperationException("Can not edit a delevered command with id "+dto.getId(), ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (!fournisseur.isPresent()){
            log.warn("Fournisseur with id "+dto.getFournisseur().getId()+" was not found");
            throw new EntityNotFoundException("Fournisseur with id "+dto.getFournisseur().getId()+" was not found", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        if ( dto.getLigneCommandeFournisseurs() !=null){
            dto.getLigneCommandeFournisseurs().forEach(ligne -> {
                if (ligne.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligne.getArticle().getId());
                    if (article.isEmpty()){
                        log.warn("Article with id "+ligne.getArticle().getId()+" was not found");
                        throw new EntityNotFoundException("Article with id "+ligne.getArticle().getId()+" was not found", ErrorCodes.CLIENT_NOT_FOUND);
                    }
                }
            });

        }
        CommandeFournisseur commande = commandeFournisseurRepository.save(CommandeFournisseurMapper.toEntity(dto));
        dto.getLigneCommandeFournisseurs().forEach(ligne -> {

            LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurMapper.toEntity(ligne);
            ligneCommandeFournisseur.setCommandeFournisseur(commande);
            ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        });

        return CommandeFournisseurMapper.toDTO(commande);
    }

    @Override
    public CommandeFournisseurDTO updateEtat(Integer idcommande, EtatCommande etatCommande) {
        if (idcommande==null){
            log.error("CommandeFournisseur id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("CommandeFournisseur etat is null");
            throw new InvalidOperationException("Can not update a command with null etat ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        CommandeFournisseurDTO commandeFournisseur = findById(idcommande);
        if (isCommandeLivree(commandeFournisseur)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeFournisseur.getId(), ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }
        commandeFournisseur.setEtat(etatCommande);
       CommandeFournisseurDTO savedCommandeFournisseur = CommandeFournisseurMapper.toDTO(commandeFournisseurRepository.save(CommandeFournisseurMapper.toEntity(commandeFournisseur)));
        if (etatCommande==EtatCommande.LIVREE){
            updateMvtStk(idcommande);
        }
        return  savedCommandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO updateQuantite(Integer idcommande, Integer idlignecommande, BigDecimal quantite) {

        if (idcommande==null){
            log.error("CommandeFournisseur id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("CommandeFournisseur idlignecommande is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (quantite==null || quantite.compareTo(BigDecimal.ZERO)==0){
            throw new InvalidOperationException("Can not edit a  command with quantite null  or zero", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        CommandeFournisseurDTO commandeFournisseur = findById(idcommande);
        if (isCommandeLivree(commandeFournisseur)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeFournisseur.getId(), ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idlignecommande);
       if (ligneCommandeFournisseurOptional.isEmpty()){
           throw new EntityNotFoundException("No lignecommandefournisseur with id "+idlignecommande+" was not found", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
       }
        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
       ligneCommandeFournisseur.setQuantite(quantite);
       ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO updateFournisseur(Integer idcommande, Integer idfournisseur) {

        if (idcommande==null){
            log.error("CommandeFournisseur id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (idfournisseur==null){
            log.error("Fournisseur idfournisseur is null");
            throw new InvalidOperationException("Can not update a command with null idclient ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        CommandeFournisseurDTO commandeFournisseur = findById(idcommande);
        if (isCommandeLivree(commandeFournisseur)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeFournisseur.getId(), ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idfournisseur);
        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("No supplier with id "+idfournisseur);
        }
        commandeFournisseur.setFournisseur(FournisseurMapper.toDTO(fournisseurOptional.get()));
        return CommandeFournisseurMapper.toDTO(commandeFournisseurRepository.save(CommandeFournisseurMapper.toEntity(commandeFournisseur)));
    }

    @Override
    public CommandeFournisseurDTO updateArticle(Integer idcommande, Integer idlignecommande, Integer idarticle) {

        if (idcommande==null){
            log.error("CommandeFournisseur id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("Lignecommande id is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (idarticle==null){
            log.error("Newidarticle id is null");
            throw new InvalidOperationException("Can not update a command with null newidarticle ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        CommandeFournisseurDTO commandeFournisseur = findById(idcommande);
        if (isCommandeLivree(commandeFournisseur)){
            throw new InvalidOperationException("Can not edit a delevered command with id "+commandeFournisseur.getId(), ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idlignecommande);
        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("No lignecommandefournisseur with id "+idlignecommande+" was not found", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        Optional<Article> articleOptional = articleRepository.findById(idarticle);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException("Article with id "+idarticle+" was not found", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
        }

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        Article article = articleOptional.get();

        ligneCommandeFournisseur.setArticle(article);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO deleteArticle(Integer idcommande, Integer idlignecommande) {

        if (idcommande==null){
            log.error("CommandeFournisseur id is null");
            throw new InvalidOperationException("Can not update a command with null id ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        if (idlignecommande==null){
            log.error("Lignecommande id is null");
            throw new InvalidOperationException("Can not update a command with null idlignecommande ", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }

        CommandeFournisseurDTO commandeFournisseur = findById(idcommande);
        if (isCommandeLivree(commandeFournisseur)){
            throw new InvalidOperationException("Can not delete a lignecommande in  delevered command with id "+idlignecommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_EDITABLE);
        }
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idlignecommande);
        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("No lignecommandefournisseur with id "+idlignecommande+" was not found", ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        ligneCommandeFournisseurRepository.deleteById(idlignecommande);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO findById(Integer id) {

        if (id==null){
            log.error("CommandeFournisseur id not found");
        }

        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findById(id);
        return Optional.of(CommandeFournisseurMapper.toDTO(commandeFournisseur.get())).orElseThrow(()-> new EntityNotFoundException("La commande "+id+" n'existe pas", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findAllLigneCommandeByCommandeId(Integer id) {
        return ligneCommandeFournisseurRepository.findByCommandeFournisseurId(id).stream()
                .map(LigneCommandeFournisseurMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CommandeFournisseurDTO findByCode(String code) {

        if (code==null){
            log.error("CommandeFournisseur code not found");
        }

        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findByCode(code);
        return Optional.of(CommandeFournisseurMapper.toDTO(commandeFournisseur.get())).orElseThrow(()-> new EntityNotFoundException("La commande avec le code "+code+" n'existe pas", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDTO> findAll() {
        return commandeFournisseurRepository.findAll().stream().map(CommandeFournisseurMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("CommandeClient id not found");
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs= ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer une commande fournisseur déjà utilisé dans les lignecommandes fournisseur", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    private boolean isCommandeLivree(CommandeFournisseurDTO dto){
        return EtatCommande.LIVREE.equals(dto.getEtat());
    }

    private void updateMvtStk(Integer idcommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idcommande);
        ligneCommandeFournisseurs.forEach(ligne -> {
            MouvementStockDTO dto = MouvementStockDTO.builder()
                    .article(ArticleMapper.toDTO(ligne.getArticle()))
                    .datemvt(Instant.now())
                    .typemvt(TypeMouvementStock.ENTREE)
                    .sourcemvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(ligne.getQuantite())
                    .build();
            mouvementStockService.sortieStock(dto);
        });
    }
}
