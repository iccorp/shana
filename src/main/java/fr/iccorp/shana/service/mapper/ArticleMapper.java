package fr.iccorp.shana.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.service.dto.ArticleDTO;

/**
 * Mapper for the entity Article and its DTO ArticleDTO.
 */
@Mapper(componentModel = "spring", uses = { CategorieMapper.class })
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.nom", target = "categorieNom")
    @Mapping(target = "photo", ignore = true)
    ArticleDTO toDto(Article article);

    @Mapping(source = "categorieId", target = "categorie")
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
