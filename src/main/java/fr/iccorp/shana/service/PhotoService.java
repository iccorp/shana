package fr.iccorp.shana.service;

import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;
import fr.iccorp.shana.service.dto.PhotoDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Photo.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
    PhotoDTO save(PhotoDTO photoDTO) throws Exception;

    /**
     * Get a photo.
     *
     * @param photoDTO the entity to save
     * @return the entity
     * @throws Exception 
     */
    PhotoDTO getPhotoByDTO(PhotoDTO photoDTO) throws Exception;
    
    /**
     * Get all the photos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PhotoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PhotoDTO findOne(Long id);

    /**
     * Delete the "id" photo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

}
