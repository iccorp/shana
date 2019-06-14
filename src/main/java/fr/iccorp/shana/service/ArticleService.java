package fr.iccorp.shana.service;

import fr.iccorp.shana.service.dto.ArticleDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Article.
 */
public interface ArticleService {

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
    ArticleDTO save(ArticleDTO articleDTO) throws Exception;

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ArticleDTO> findAll(Pageable pageable);

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    List<ArticleDTO> findByCategory(Long categorieId);

    /**
     * Get the "id" article.
     *
     * @param id the id of the entity
     * @return the entity
     * @throws Exception 
     */
    ArticleDTO findOne(Long id) throws Exception;

    /**
     * Delete the "id" article.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
