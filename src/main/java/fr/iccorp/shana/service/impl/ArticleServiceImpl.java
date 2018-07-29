package fr.iccorp.shana.service.impl;

import fr.iccorp.shana.service.ArticleService;
import fr.iccorp.shana.service.PhotoService;
import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;
import fr.iccorp.shana.repository.ArticleRepository;
import fr.iccorp.shana.service.dto.ArticleDTO;
import fr.iccorp.shana.service.dto.PhotoDTO;
import fr.iccorp.shana.service.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;


/**
 * Service Implementation for managing Article.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;
    
    private PhotoService photoService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper, PhotoService photoService) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.photoService = photoService;
    }

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
    @Override
    public ArticleDTO save(ArticleDTO articleDTO) throws Exception {
        log.debug("Request to save Article : {}", articleDTO);
        if (articleDTO.getPhoto() != null) {
        	PhotoDTO photoDTO = new PhotoDTO();
        	photoDTO.setPhoto(articleDTO.getPhoto());
        	photoDTO = photoService.save(photoDTO);
        	articleDTO.setIdPhoto(photoDTO.getIdPhoto());
        }
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable)
            .map(articleMapper::toDto);
    }

    /**
     * Get one article by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArticleDTO findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        Article article = articleRepository.findOne(id);
        ArticleDTO articleDTO = articleMapper.toDto(article);
        if (articleDTO.getIdPhoto() != null) {
        	PhotoDTO photoDTO = new PhotoDTO();
        	photoDTO.setIdPhoto(articleDTO.getIdPhoto());
        	photoDTO.setPhotoContentType(articleDTO.getPhotoContentType());
        	photoDTO.setFormat(FORMAT_PHOTO.VIGNETTE);
        	photoDTO = photoService.getPhotoByDTO(photoDTO);
        	articleDTO.setPhoto(photoDTO.getPhoto());
        }
        return articleDTO;
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.delete(id);
    }
}
