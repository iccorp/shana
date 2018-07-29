package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.AbonneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Abonne and its DTO AbonneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AbonneMapper extends EntityMapper<AbonneDTO, Abonne> {

    

    

    default Abonne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Abonne abonne = new Abonne();
        abonne.setId(id);
        return abonne;
    }
}
