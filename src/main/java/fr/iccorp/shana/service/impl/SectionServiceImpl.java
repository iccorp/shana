package fr.iccorp.shana.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.iccorp.shana.domain.Section;
import fr.iccorp.shana.repository.SectionRepository;
import fr.iccorp.shana.service.SectionService;
import fr.iccorp.shana.service.dto.SectionDTO;
import fr.iccorp.shana.service.mapper.SectionMapper;

/**
 * Service Implementation for managing Section.
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final Logger log = LoggerFactory.getLogger(SectionServiceImpl.class);

    private final SectionRepository sectionRepository;

    private final SectionMapper sectionMapper;

    public SectionServiceImpl(SectionRepository sectionRepository, SectionMapper sectionMapper) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
    }

    /**
     * Save a section.
     *
     * @param sectionDTO
     *            the entity to save
     * @return the persisted entity
     */
    @Override
    public SectionDTO save(SectionDTO sectionDTO) {
        log.debug("Request to save Section : {}", sectionDTO);
        Section section = sectionMapper.toEntity(sectionDTO);
        section = sectionRepository.save(section);
        return sectionMapper.toDto(section);
    }

    /**
     * Get all the sections.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sections");
        return sectionRepository.findAll(pageable).map(sectionMapper::toDto);
    }

    /**
     * Get one section by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SectionDTO findOne(Long id) {
        log.debug("Request to get Section : {}", id);
        Section section = sectionRepository.findOne(id);
        return sectionMapper.toDto(section);
    }

    /**
     * Delete the section by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Section : {}", id);
        sectionRepository.delete(id);
    }

    @Override
    public List<SectionDTO> findByArticleId(Long id) {
        log.debug("Request to find Section by articleID: {}", id);
        return sectionMapper.toDto(sectionRepository.findByArticleId(id));
    }
}
