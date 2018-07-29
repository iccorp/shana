package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Article and its DTO ArticleDTO.
 */
@Mapper(componentModel = "spring", uses = {CategorieMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.nom", target = "categorieNom")
    ArticleDTO toDto(Article article); 

    @Mapping(source = "categorieId", target = "categorie")
    @Mapping(target = "sections", ignore = true)
    @Mapping(target = "photo", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
