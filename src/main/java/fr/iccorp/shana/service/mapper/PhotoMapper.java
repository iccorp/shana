package fr.iccorp.shana.service.mapper;

import fr.iccorp.shana.domain.*;
import fr.iccorp.shana.service.dto.ArticleDTO;
import fr.iccorp.shana.service.dto.PhotoDTO;

import java.util.List;

import org.mapstruct.*;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    

	List<PhotoDTO> toDto(List<Photo> entityList);
    
	@Mapping(target = "photo", ignore = true)
	@Mapping(target = "format", ignore = true)
    Photo toEntity(PhotoDTO photoDTO);

	@Mapping(target = "photo", ignore = true)
	@Mapping(target = "format", ignore = true)
	PhotoDTO toDto(Photo photo);
	
    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
