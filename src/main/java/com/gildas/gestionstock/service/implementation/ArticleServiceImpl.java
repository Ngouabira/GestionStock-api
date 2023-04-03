package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.ArticleDTO;
import com.gildas.gestionstock.dto.LigneCommandeClientDTO;
import com.gildas.gestionstock.dto.LigneCommandeFournisseurDTO;
import com.gildas.gestionstock.dto.LigneVenteDTO;
import com.gildas.gestionstock.entity.Article;
import com.gildas.gestionstock.entity.LigneCommandeClient;
import com.gildas.gestionstock.entity.LigneCommandeFournisseur;
import com.gildas.gestionstock.entity.LigneVente;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.ArticleMapper;
import com.gildas.gestionstock.mapper.LigneCommandeClientMapper;
import com.gildas.gestionstock.mapper.LigneCommandeFournisseurMapper;
import com.gildas.gestionstock.mapper.LigneVenteMapper;
import com.gildas.gestionstock.repository.*;
import com.gildas.gestionstock.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    @Override
    public ArticleDTO save(ArticleDTO dto) {
        return ArticleMapper.toDTO(articleRepository.save(ArticleMapper.toEntity(dto)));
    }

    @Override
    public ArticleDTO findById(Integer id) {
        if (id==null){
            log.error("Article id is null");
        }
        Optional<Article> article = articleRepository.findById(id);
        return Optional.of(ArticleMapper.toDTO(article.get())).orElseThrow(()-> new EntityNotFoundException(
            "Aucun article avec l'id "+id+" n'a été trouvé",
            ErrorCodes.ARTICLE_NOT_FOUND
        ));
    }

    @Override
    public ArticleDTO findByCodeArticle(String code) {
        if (code==null){
            log.error("Article code is null");
        }
        Optional<Article> article = articleRepository.findByCode(code);
        return Optional.of(ArticleMapper.toDTO(article.get())).orElseThrow(()-> new EntityNotFoundException(
                "Aucun article avec le code "+code+" n'a été trouvé",
                ErrorCodes.ARTICLE_NOT_FOUND
        ));
    }

    @Override
    public List<ArticleDTO> findAll() {
        return articleRepository.findAll().stream().map(ArticleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDTO> findHistoriqueVente(Integer idarticle) {
        return ligneVenteRepository.findAllByArticleId(idarticle).stream()
                .map(LigneVenteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDTO> findHistoriqueCommandeClient(Integer idarticle) {
        return ligneCommandeClientRepository.findAllByArticleId(idarticle).stream()
                .map(LigneCommandeClientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findHistoriqueCommandeFournisseur(Integer idarticle) {
        return ligneCommandeFournisseurRepository.findAllByArticleId(idarticle).stream()
                .map(LigneCommandeFournisseurMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findAllByIdCategorie(Integer idcategorie) {
        return articleRepository.findAllByCategorieId(idcategorie).stream()
                .map(ArticleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Article id is null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans les commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs= ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans les commandes fournisseur", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans les ventes", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        articleRepository.deleteById(id);
    }
}
