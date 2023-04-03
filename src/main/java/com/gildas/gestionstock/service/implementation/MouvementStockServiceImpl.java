package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.MouvementStockDTO;
import com.gildas.gestionstock.entity.Article;
import com.gildas.gestionstock.entity.MouvementStock;
import com.gildas.gestionstock.entity.TypeMouvementStock;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.mapper.MouvementStockMapper;
import com.gildas.gestionstock.repository.ArticleRepository;
import com.gildas.gestionstock.repository.MouvemetStockRepository;
import com.gildas.gestionstock.service.MouvementStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MouvementStockServiceImpl implements MouvementStockService {

    MouvemetStockRepository mouvemetStockRepository;
    ArticleRepository articleRepository;

    @Override
    public MouvementStockDTO save(MouvementStockDTO dto) {
        return MouvementStockMapper.toDTO(mouvemetStockRepository.save(MouvementStockMapper.toEntity(dto))) ;
    }

    @Override
    public MouvementStockDTO findById(Integer id) {
        if (id==null){
            log.error("Id est nul");
        }
        Optional<MouvementStock> mouvementStock = mouvemetStockRepository.findById(id);
        return Optional.of(MouvementStockMapper.toDTO(mouvementStock.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Le mouvement stock avec l'id "+id+" n'existe pas",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public List<MouvementStockDTO> findAll() {
        return mouvemetStockRepository.findAll().stream().map(MouvementStockMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Id est nul");
            return;
        }
        mouvemetStockRepository.deleteById(id);
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {

        if (idArticle==null){
            log.error("Article id is null");
            return BigDecimal.valueOf(-1);
        }
        Optional<Article> article = articleRepository.findById(idArticle);
        if (article.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun article avec l'id "+idArticle+" n'a été trouvé",
                    ErrorCodes.ARTICLE_NOT_FOUND
            );
        }
        return mouvemetStockRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementStockDTO> mvtStkArticle(Integer idArticle) {
        if (idArticle==null){
            log.error("Article id is null");
        }
        Optional<Article> article = articleRepository.findById(idArticle);
        if (article.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun article avec l'id "+idArticle+" n'a été trouvé",
                    ErrorCodes.ARTICLE_NOT_FOUND
            );
        }

        return mouvemetStockRepository.findAllByArticleId(idArticle).stream()
                .map(MouvementStockMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MouvementStockDTO entreeStock(MouvementStockDTO dto) {
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())));
        dto.setTypemvt(TypeMouvementStock.ENTREE);
        return MouvementStockMapper.toDTO(mouvemetStockRepository.save(MouvementStockMapper.toEntity(dto)));
    }

    @Override
    public MouvementStockDTO sortieStock(MouvementStockDTO dto) {
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue() * -1)));
        dto.setTypemvt(TypeMouvementStock.SORTIE);
        return MouvementStockMapper.toDTO(mouvemetStockRepository.save(MouvementStockMapper.toEntity(dto)));
    }

    @Override
    public MouvementStockDTO correctionStockPos(MouvementStockDTO dto) {
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())));
        dto.setTypemvt(TypeMouvementStock.CORRECTION_POSITIVE);
        return MouvementStockMapper.toDTO(mouvemetStockRepository.save(MouvementStockMapper.toEntity(dto)));
    }

    @Override
    public MouvementStockDTO correctionStockNeg(MouvementStockDTO dto) {
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue() * -1)));
        dto.setTypemvt(TypeMouvementStock.CORRECTION_NEGATIVE);
        return MouvementStockMapper.toDTO(mouvemetStockRepository.save(MouvementStockMapper.toEntity(dto)));
    }

}
