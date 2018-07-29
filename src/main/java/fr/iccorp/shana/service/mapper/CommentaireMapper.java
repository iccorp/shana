package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.CommentaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commentaire and its DTO CommentaireDTO.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class, AbonneMapper.class})
public interface CommentaireMapper extends EntityMapper<CommentaireDTO, Commentaire> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "article.nom", target = "articleNom")
    @Mapping(source = "abonne.id", target = "abonneId")
    @Mapping(source = "abonne.pseudo", target = "abonnePseudo")
    CommentaireDTO toDto(Commentaire commentaire); 

    @Mapping(source = "articleId", target = "article")
    @Mapping(source = "abonneId", target = "abonne")
    Commentaire toEntity(CommentaireDTO commentaireDTO);

    default Commentaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commentaire commentaire = new Commentaire();
        commentaire.setId(id);
        return commentaire;
    }
}
