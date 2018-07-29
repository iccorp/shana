package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.Categorie;
import fr.iccorp.shana.service.dto.CategorieDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-07-01T12:35:37+0200",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.100.v20171123-1049, environment: Java 1.8.0_141 (Oracle Corporation)"
)
@Component
public class CategorieMapperImpl implements CategorieMapper {

    @Override
    public CategorieDTO toDto(Categorie entity) {
        if ( entity == null ) {
            return null;
        }

        CategorieDTO categorieDTO = new CategorieDTO();

        categorieDTO.setId( entity.getId() );
        categorieDTO.setNom( entity.getNom() );

        return categorieDTO;
    }

    @Override
    public List<Categorie> toEntity(List<CategorieDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Categorie> list = new ArrayList<Categorie>( dtoList.size() );
        for ( CategorieDTO categorieDTO : dtoList ) {
            list.add( toEntity( categorieDTO ) );
        }

        return list;
    }

    @Override
    public List<CategorieDTO> toDto(List<Categorie> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategorieDTO> list = new ArrayList<CategorieDTO>( entityList.size() );
        for ( Categorie categorie : entityList ) {
            list.add( toDto( categorie ) );
        }

        return list;
    }

    @Override
    public Categorie toEntity(CategorieDTO categorieDTO) {
        if ( categorieDTO == null ) {
            return null;
        }

        Categorie categorie = new Categorie();

        categorie.setId( categorieDTO.getId() );
        categorie.setNom( categorieDTO.getNom() );

        return categorie;
    }
}
