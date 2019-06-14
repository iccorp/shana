package fr.iccorp.shana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.domain.Categorie;
import fr.iccorp.shana.repository.ArticleRepository;
import fr.iccorp.shana.repository.CategorieRepository;
import fr.iccorp.shana.service.ArticleService;
import fr.iccorp.shana.service.PhotoService;
import fr.iccorp.shana.service.dto.ArticleDTO;
import fr.iccorp.shana.service.mapper.ArticleMapper;

/**
 * Service Implementation for managing Article.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final CategorieRepository categorieRepository;

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper,
            PhotoService photoService, CategorieRepository categorieRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Save a article.
     *
     * @param articleDTO
     *            the entity to save
     * @return the persisted entity
     * @throws Exception
     */
    @Override
    public ArticleDTO save(ArticleDTO articleDTO) throws Exception {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        return articleMapper.toDto(articleRepository.save(article));
    }

    /**
     * Get all the articles.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable).map(articleMapper::toDto);
    }

    /**
     * Get one article by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public ArticleDTO findOne(Long id) throws Exception {
        log.debug("Request to get Article : {}", id);
        Article article = articleRepository.findOne(id);
        ArticleDTO articleDTO = articleMapper.toDto(article);
        return articleDTO;
    }

    /**
     * Delete the article by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.delete(id);
    }

	@Override
	public List<ArticleDTO> findByCategory(Long categorieId) {
		log.debug("Request to find Article by categorieId : {}", categorieId);
		Categorie category = categorieRepository.findOne(categorieId);
		log.debug("Categorie: {}", category);
		List<Article> res = articleRepository.findByCategorie(category);
		log.debug("Categorie: {}", res);
		return articleMapper.toDto(res);
	}
}
