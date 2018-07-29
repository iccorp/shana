package fr.iccorp.shana.service.impl;

import fr.iccorp.shana.service.AbonneService;
import fr.iccorp.shana.domain.Abonne;
import fr.iccorp.shana.repository.AbonneRepository;
import fr.iccorp.shana.service.dto.AbonneDTO;
import fr.iccorp.shana.service.mapper.AbonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Abonne.
 */
@Service
@Transactional
public class AbonneServiceImpl implements AbonneService{

    private final Logger log = LoggerFactory.getLogger(AbonneServiceImpl.class);

    private final AbonneRepository abonneRepository;

    private final AbonneMapper abonneMapper;

    public AbonneServiceImpl(AbonneRepository abonneRepository, AbonneMapper abonneMapper) {
        this.abonneRepository = abonneRepository;
        this.abonneMapper = abonneMapper;
    }

    /**
     * Save a abonne.
     *
     * @param abonneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AbonneDTO save(AbonneDTO abonneDTO) {
        log.debug("Request to save Abonne : {}", abonneDTO);
        Abonne abonne = abonneMapper.toEntity(abonneDTO);
        abonne = abonneRepository.save(abonne);
        return abonneMapper.toDto(abonne);
    }

    /**
     * Get all the abonnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AbonneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Abonnes");
        return abonneRepository.findAll(pageable)
            .map(abonneMapper::toDto);
    }

    /**
     * Get one abonne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AbonneDTO findOne(Long id) {
        log.debug("Request to get Abonne : {}", id);
        Abonne abonne = abonneRepository.findOne(id);
        return abonneMapper.toDto(abonne);
    }

    /**
     * Delete the abonne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Abonne : {}", id);
        abonneRepository.delete(id);
    }
}
