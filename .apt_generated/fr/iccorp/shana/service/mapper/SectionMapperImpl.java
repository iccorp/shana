package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.domain.Section;
import fr.iccorp.shana.service.dto.SectionDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-07-01T14:13:55+0200",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.100.v20171123-1049, environment: Java 1.8.0_141 (Oracle Corporation)"
)
@Component
public class SectionMapperImpl implements SectionMapper {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<SectionDTO> toDto(List<Section> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SectionDTO> list = new ArrayList<SectionDTO>( arg0.size() );
        for ( Section section : arg0 ) {
            list.add( toDto( section ) );
        }

        return list;
    }

    @Override
    public List<Section> toEntity(List<SectionDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Section> list = new ArrayList<Section>( arg0.size() );
        for ( SectionDTO sectionDTO : arg0 ) {
            list.add( toEntity( sectionDTO ) );
        }

        return list;
    }

    @Override
    public SectionDTO toDto(Section section) {
        if ( section == null ) {
            return null;
        }

        SectionDTO sectionDTO = new SectionDTO();

        Long id = sectionArticleId( section );
        if ( id != null ) {
            sectionDTO.setArticleId( id );
        }
        String nom = sectionArticleNom( section );
        if ( nom != null ) {
            sectionDTO.setArticleNom( nom );
        }
        sectionDTO.setId( section.getId() );
        byte[] photo = section.getPhoto();
        if ( photo != null ) {
            sectionDTO.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        sectionDTO.setPhotoContentType( section.getPhotoContentType() );
        sectionDTO.setTextApres( section.getTextApres() );
        sectionDTO.setTextAvant( section.getTextAvant() );
        sectionDTO.setTitre( section.getTitre() );

        return sectionDTO;
    }

    @Override
    public Section toEntity(SectionDTO sectionDTO) {
        if ( sectionDTO == null ) {
            return null;
        }

        Section section = new Section();

        section.setArticle( articleMapper.fromId( sectionDTO.getArticleId() ) );
        section.setId( sectionDTO.getId() );
        byte[] photo = sectionDTO.getPhoto();
        if ( photo != null ) {
            section.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        section.setPhotoContentType( sectionDTO.getPhotoContentType() );
        section.setTextApres( sectionDTO.getTextApres() );
        section.setTextAvant( sectionDTO.getTextAvant() );
        section.setTitre( sectionDTO.getTitre() );

        return section;
    }

    private Long sectionArticleId(Section section) {
        if ( section == null ) {
            return null;
        }
        Article article = section.getArticle();
        if ( article == null ) {
            return null;
        }
        Long id = article.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sectionArticleNom(Section section) {
        if ( section == null ) {
            return null;
        }
        Article article = section.getArticle();
        if ( article == null ) {
            return null;
        }
        String nom = article.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }
}
