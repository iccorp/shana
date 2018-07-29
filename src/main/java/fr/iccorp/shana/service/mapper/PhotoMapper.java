package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.ArticleDTO;
import fr.iccorp.shana.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    

    
	@Mapping(target = "photo", ignore = true)
    Photo toEntity(PhotoDTO photoDTO);
	
    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
