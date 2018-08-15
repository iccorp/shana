package fr.iccorp.shana.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.iccorp.shana.service.SectionService;
import fr.iccorp.shana.service.dto.SectionDTO;
import fr.iccorp.shana.web.rest.errors.BadRequestAlertException;
import fr.iccorp.shana.web.rest.util.HeaderUtil;
import fr.iccorp.shana.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Section.
 */
@RestController
@RequestMapping("/api")
public class SectionResource {

    private final Logger log = LoggerFactory.getLogger(SectionResource.class);

    private static final String ENTITY_NAME = "section";

    private final SectionService sectionService;

    public SectionResource(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    /**
     * POST /sections : Create a new section.
     *
     * @param sectionDTO
     *            the sectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         sectionDTO, or with status 400 (Bad Request) if the section has
     *         already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/sections")
    @Timed
    public ResponseEntity<SectionDTO> createSection(@RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to save Section : {}", sectionDTO);
        if (sectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new section cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.created(new URI("/api/sections/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /sections : Updates an existing section.
     *
     * @param sectionDTO
     *            the sectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         sectionDTO, or with status 400 (Bad Request) if the sectionDTO is not
     *         valid, or with status 500 (Internal Server Error) if the sectionDTO
     *         couldn't be updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/sections")
    @Timed
    public ResponseEntity<SectionDTO> updateSection(@RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to update Section : {}", sectionDTO);
        if (sectionDTO.getId() == null) {
            return createSection(sectionDTO);
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionDTO.getId().toString())).body(result);
    }

    /**
     * GET /sections : get all the sections.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sections in
     *         body
     */
    @GetMapping("/sections")
    @Timed
    public ResponseEntity<List<SectionDTO>> getAllSections(Pageable pageable) {
        log.debug("REST request to get a page of Sections");
        Page<SectionDTO> page = sectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /sections/:id : get the "id" section.
     *
     * @param id
     *            the id of the sectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionDTO,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/sections/{id}")
    @Timed
    public ResponseEntity<SectionDTO> getSection(@PathVariable Long id) {
        log.debug("REST request to get Section : {}", id);
        SectionDTO sectionDTO = sectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionDTO));
    }

    /**
     * GET /sections/:id : get the "id" section.
     *
     * @param id
     *            the id of the sectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionDTO,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/sections/article/{id}")
    @Timed
    public ResponseEntity<List<SectionDTO>> getSectionByIdArticle(@PathVariable Long id) {
        log.debug("REST request to get Section : {}", id);
        List<SectionDTO> sections = sectionService.findByArticleId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sections));
    }

    /**
     * DELETE /sections/:id : delete the "id" section.
     *
     * @param id
     *            the id of the sectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sections/{id}")
    @Timed
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        log.debug("REST request to delete Section : {}", id);
        sectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
