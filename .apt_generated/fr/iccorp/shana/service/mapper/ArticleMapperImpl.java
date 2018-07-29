package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.domain.Categorie;
import fr.iccorp.shana.service.dto.ArticleDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-07-01T14:01:44+0200",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.100.v20171123-1049, environment: Java 1.8.0_141 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Autowired
    private CategorieMapper categorieMapper;

    @Override
    public List<Article> toEntity(List<ArticleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Article> list = new ArrayList<Article>( dtoList.size() );
        for ( ArticleDTO articleDTO : dtoList ) {
            list.add( toEntity( articleDTO ) );
        }

        return list;
    }

    @Override
    public List<ArticleDTO> toDto(List<Article> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ArticleDTO> list = new ArrayList<ArticleDTO>( entityList.size() );
        for ( Article article : entityList ) {
            list.add( toDto( article ) );
        }

        return list;
    }

    @Override
    public ArticleDTO toDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDTO articleDTO = new ArticleDTO();

        String nom = articleCategorieNom( article );
        if ( nom != null ) {
            articleDTO.setCategorieNom( nom );
        }
        Long id = articleCategorieId( article );
        if ( id != null ) {
            articleDTO.setCategorieId( id );
        }
        articleDTO.setId( article.getId() );
        articleDTO.setNom( article.getNom() );
        byte[] photo = article.getPhoto();
        if ( photo != null ) {
            articleDTO.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        articleDTO.setPhotoContentType( article.getPhotoContentType() );
        articleDTO.setTitre( article.getTitre() );
        articleDTO.setPosition( article.getPosition() );
        articleDTO.setPositionDansCategorie( article.getPositionDansCategorie() );
        articleDTO.setDateCreation( article.getDateCreation() );
        articleDTO.setDateDerniereModification( article.getDateDerniereModification() );
        articleDTO.setNbVue( article.getNbVue() );
        articleDTO.setNbLike( article.getNbLike() );
        articleDTO.setNbPartage( article.getNbPartage() );

        return articleDTO;
    }

    @Override
    public Article toEntity(ArticleDTO articleDTO) {
        if ( articleDTO == null ) {
            return null;
        }

        Article article = new Article();

        article.setCategorie( categorieMapper.fromId( articleDTO.getCategorieId() ) );
        article.setDateCreation( articleDTO.getDateCreation() );
        article.setDateDerniereModification( articleDTO.getDateDerniereModification() );
        article.setId( articleDTO.getId() );
        article.setNbLike( articleDTO.getNbLike() );
        article.setNbPartage( articleDTO.getNbPartage() );
        article.setNbVue( articleDTO.getNbVue() );
        article.setNom( articleDTO.getNom() );
        byte[] photo = articleDTO.getPhoto();
        if ( photo != null ) {
            article.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        article.setPhotoContentType( articleDTO.getPhotoContentType() );
        article.setPosition( articleDTO.getPosition() );
        article.setPositionDansCategorie( articleDTO.getPositionDansCategorie() );
        article.setTitre( articleDTO.getTitre() );

        return article;
    }

    private String articleCategorieNom(Article article) {
        if ( article == null ) {
            return null;
        }
        Categorie categorie = article.getCategorie();
        if ( categorie == null ) {
            return null;
        }
        String nom = categorie.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private Long articleCategorieId(Article article) {
        if ( article == null ) {
            return null;
        }
        Categorie categorie = article.getCategorie();
        if ( categorie == null ) {
            return null;
        }
        Long id = categorie.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
