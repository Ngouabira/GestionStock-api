package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.AdresseDTO;
import com.gildas.gestionstock.entity.Adresse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdresseMapper {

    public static AdresseDTO toDTO(Adresse adresse){

        if (adresse==null){
            return null;
        }
        else{
            return AdresseDTO.builder()
                    .adresse1(adresse.getAdresse1())
                    .adresse2(adresse.getAdresse2())
                    .ville(adresse.getVille())
                    .codePostale(adresse.getCodePostale())
                    .pays(adresse.getPays())
                    .build();
        }
    }

    public static Adresse  toEntity(AdresseDTO adresseDTO){

        if (adresseDTO==null){
            return null;
        }
        else{
            Adresse adresse = new Adresse();
            adresse.setAdresse1(adresseDTO.getAdresse1());
            adresse.setAdresse2(adresseDTO.getAdresse2());
            adresse.setVille(adresseDTO.getVille());
            adresse.setCodePostale(adresseDTO.getCodePostale());
            adresse.setPays(adresseDTO.getPays());
            return adresse;
        }
    }
}
