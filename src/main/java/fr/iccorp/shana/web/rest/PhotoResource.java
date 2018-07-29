package fr.iccorp.shana.web.rest;

import java.net.URI;
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

import fr.iccorp.shana.service.PhotoService;
import fr.iccorp.shana.service.dto.PhotoDTO;
import fr.iccorp.shana.web.rest.errors.BadRequestAlertException;
import fr.iccorp.shana.web.rest.util.HeaderUtil;
import fr.iccorp.shana.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private final Logger log = LoggerFactory.getLogger(PhotoResource.class);

    private static final String ENTITY_NAME = "photo";

    private final PhotoService photoService;

    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * POST  /photos : Create a new photo.
     *
     * @param photoDTO the photoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photoDTO, or with status 400 (Bad Request) if the photo has already an ID
     * @throws Exception 
     */
    @PostMapping("/photos")
    @Timed
    public ResponseEntity<PhotoDTO> createPhoto(@RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to save Photo : {}", photoDTO);
        if (photoDTO.getId() != null) {
            throw new BadRequestAlertException("A new photo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /photos : get a photo in a specified format.
     *
     * @param photoDTO the photoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photoDTO, or with status 400 (Bad Request) if the photo has already an ID
     * @throws Exception 
     */
    @PostMapping("/photos")
    @Timed
    public ResponseEntity<PhotoDTO> getPhotoByDTO(@RequestBody PhotoDTO photoDTO) throws Exception {
    	log.debug("REST request to save Photo : {}", photoDTO);
    	if (photoDTO.getIdPhoto() == null || photoDTO.getFormat() == null) {
    		throw new BadRequestAlertException("You must specify a format and a correct idPhoto to get a PHOTO", ENTITY_NAME, "format not provided");
    	}
    	PhotoDTO result = photoService.save(photoDTO);
    	return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
    			.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
    			.body(result);
    }
    
    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photoDTO the photoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photoDTO,
     * or with status 400 (Bad Request) if the photoDTO is not valid,
     * or with status 500 (Internal Server Error) if the photoDTO couldn't be updated
     * @throws Exception 
     */
    @PutMapping("/photos")
    @Timed
    public ResponseEntity<PhotoDTO> updatePhoto(@RequestBody PhotoDTO photoDTO) throws Exception {
        log.debug("REST request to update Photo : {}", photoDTO);
        if (photoDTO.getId() == null) {
            return createPhoto(photoDTO);
        }
        PhotoDTO result = photoService.save(photoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photos")
    @Timed
    public ResponseEntity<List<PhotoDTO>> getAllPhotos(Pageable pageable) {
        log.debug("REST request to get a page of Photos");
        Page<PhotoDTO> page = photoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/photos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    @Timed
    public ResponseEntity<PhotoDTO> getPhoto(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        PhotoDTO photoDTO = photoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(photoDTO));
    }

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        photoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
