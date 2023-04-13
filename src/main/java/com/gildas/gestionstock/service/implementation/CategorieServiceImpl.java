package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.CategorieDTO;
import com.gildas.gestionstock.entity.Article;
import com.gildas.gestionstock.entity.Categorie;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.CategorieMapper;
import com.gildas.gestionstock.repository.ArticleRepository;
import com.gildas.gestionstock.repository.CategorieRepository;
import com.gildas.gestionstock.service.CategorieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    CategorieRepository categorieRepository;
    ArticleRepository articleRepository;

    @Override
    public CategorieDTO save(CategorieDTO dto) {
        return CategorieMapper.toDTO(categorieRepository.save(CategorieMapper.toEntity(dto))) ;
    }

    @Override
    public CategorieDTO findById(Integer id) {
        if (id==null){
            log.error("Id categorie est nul");
        }
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return Optional.of(CategorieMapper.toDTO(categorie.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "La catégorie avec l'id "+id+" n'existe pas",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
                );
    }

    @Override
    public List<CategorieDTO> findAll() {
        return categorieRepository.findAll().stream().map(CategorieMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Id est nul");
            return;
        }
        List<Article> articles = articleRepository.findAllByCategorieId(id);
        if (!articles.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer une catégorie qui a des articles", ErrorCodes.CATEGORIE_ALREADY_IN_USE);
        }

        categorieRepository.deleteById(id);
    }
}
