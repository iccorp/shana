package fr.iccorp.shana.service;

import fr.iccorp.shana.service.dto.CategorieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Categorie.
 */
public interface CategorieService {

    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save
     * @return the persisted entity
     */
    CategorieDTO save(CategorieDTO categorieDTO);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategorieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategorieDTO findOne(Long id);

    /**
     * Delete the "id" categorie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
