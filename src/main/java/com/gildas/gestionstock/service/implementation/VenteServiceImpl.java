package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.dto.VenteDTO;
import com.gildas.gestionstock.entity.*;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.ArticleMapper;
import com.gildas.gestionstock.mapper.LigneVenteMapper;
import com.gildas.gestionstock.mapper.VenteMapper;
import com.gildas.gestionstock.repository.ArticleRepository;
import com.gildas.gestionstock.repository.LigneVenteRepository;
import com.gildas.gestionstock.repository.VenteRepository;
import com.gildas.gestionstock.service.MouvementStockService;
import com.gildas.gestionstock.service.VenteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class VenteServiceImpl implements VenteService {

    VenteRepository venteRepository;
    ArticleRepository articleRepository;
    LigneVenteRepository ligneVenteRepository;

    MouvementStockService mouvementStockService;

    @Override
    public VenteDTO save(VenteDTO dto) {

        dto.getLigneVentes().forEach(ligneVenteDTO -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDTO.getArticle().getId());
            if (article.isEmpty()){
                log.warn("Article with id "+ligneVenteDTO.getArticle().getId()+" was not found");
                throw new EntityNotFoundException("Article with id "+ligneVenteDTO.getArticle().getId()+" was not found", ErrorCodes.ARTICLE_NOT_FOUND);
            }
        });

        Vente vente = venteRepository.save(VenteMapper.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDTO -> {
            LigneVente ligneVente = LigneVenteMapper.toEntity(ligneVenteDTO);
            ligneVente.setVente(vente);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });
        return VenteMapper.toDTO(vente);
    }

    @Override
    public VenteDTO findById(Integer id) {
        if (id==null){
            log.error("Vente Id is null");
        }
        Optional<Vente> vente = venteRepository.findById(id);
        return Optional.of(VenteMapper.toDTO(vente.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "La vente avec le code  "+id+" n'existe pas",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public VenteDTO findByCode(String code) {
        if (code==null){
            log.error("Vente code is null");
        }
        Optional<Vente> vente = venteRepository.findByCode(code);
        return Optional.of(VenteMapper.toDTO(vente.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "La vente avec le code  "+code+" n'existe pas",
                        ErrorCodes.VENTE_NOT_FOUND
                )
        );
    }

    @Override
    public List<VenteDTO> findAll() {
        return venteRepository.findAll().stream().map(VenteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Id is null");
            return;
        }
        List<LigneVente> ligneVentes= ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer une vente déjà utilisé dans les ligneventes", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        venteRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente ligneVente){

            MouvementStockDTO dto = MouvementStockDTO.builder()
                    .article(ArticleMapper.toDTO(ligneVente.getArticle()))
                    .datemvt(Instant.now())
                    .typemvt(TypeMouvementStock.ENTREE)
                    .sourcemvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(ligneVente.getQuantite())
                    .build();
            mouvementStockService.sortieStock(dto);

    }
}
