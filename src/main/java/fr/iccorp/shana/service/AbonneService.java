package fr.iccorp.shana.service;

import fr.iccorp.shana.service.dto.AbonneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Abonne.
 */
public interface AbonneService {

    /**
     * Save a abonne.
     *
     * @param abonneDTO the entity to save
     * @return the persisted entity
     */
    AbonneDTO save(AbonneDTO abonneDTO);

    /**
     * Get all the abonnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AbonneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" abonne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AbonneDTO findOne(Long id);

    /**
     * Delete the "id" abonne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
