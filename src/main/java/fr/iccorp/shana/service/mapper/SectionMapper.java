package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.SectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Section and its DTO SectionDTO.
 */
@Mapper(componentModel = "spring", uses = {ArticleMapper.class})
public interface SectionMapper extends EntityMapper<SectionDTO, Section> {

    @Mapping(source = "article.id", target = "articleId")
    @Mapping(source = "article.nom", target = "articleNom")
    @Mapping(target = "photo", ignore = true)
    SectionDTO toDto(Section section);

    @Mapping(source = "articleId", target = "article")
    @Mapping(target = "photo", ignore = true)
    Section toEntity(SectionDTO sectionDTO);

    default Section fromId(Long id) {
        if (id == null) {
            return null;
        }
        Section section = new Section();
        section.setId(id);
        return section;
    }
}
