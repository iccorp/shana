package fr.iccorp.shana.service;

import fr.iccorp.shana.service.dto.SectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Section.
 */
public interface SectionService {

    /**
     * Save a section.
     *
     * @param sectionDTO
     *            the entity to save
     * @return the persisted entity
     */
    SectionDTO save(SectionDTO sectionDTO);

    /**
     * Get all the sections.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    Page<SectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" section.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    SectionDTO findOne(Long id);

    /**
     * Get the section.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    List<SectionDTO> findByArticleId(Long id);

    /**
     * Delete the "id" section.
     *
     * @param id
     *            the id of the entity
     */
    void delete(Long id);
}
