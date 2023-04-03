package com.gildas.gestionstock.service.implementation;

import com.gildas.gestionstock.dto.FournisseurDTO;
import com.gildas.gestionstock.entity.CommandeFournisseur;
import com.gildas.gestionstock.entity.Fournisseur;
import com.gildas.gestionstock.entity.LigneCommandeFournisseur;
import com.gildas.gestionstock.exception.EntityNotFoundException;
import com.gildas.gestionstock.exception.ErrorCodes;
import com.gildas.gestionstock.exception.InvalidOperationException;
import com.gildas.gestionstock.mapper.FournisseurMapper;
import com.gildas.gestionstock.repository.CommandeFournisseurRepository;
import com.gildas.gestionstock.repository.FournisseurRepository;
import com.gildas.gestionstock.service.FournisseurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    FournisseurRepository fournisseurRepository;
    CommandeFournisseurRepository commandeFournisseurRepository;
    @Override
    public FournisseurDTO save(FournisseurDTO dto) {
        return FournisseurMapper.toDTO(fournisseurRepository.save(FournisseurMapper.toEntity(dto))) ;
    }

    @Override
    public FournisseurDTO findById(Integer id) {
        if (id==null){
            log.error("Id du fournisseur est nul");
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return Optional.of(FournisseurMapper.toDTO(fournisseur.get())).orElseThrow(()->
                new EntityNotFoundException(
                        "Le fournisseur avec l'id "+id+" n'existe pas",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

    @Override
    public List<FournisseurDTO> findAll() {
        return fournisseurRepository.findAll().stream().map(FournisseurMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            log.error("Id est nul");
            return;
        }

        List<CommandeFournisseur> commandeFournisseurs= commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseurs.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer un fournisseur déjà utilisé dans les commandes fournisseur", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        fournisseurRepository.deleteById(id);
    }
}
