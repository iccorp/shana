package fr.iccorp.shana.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.iccorp.shana.config.SettingsConfiguration;
import fr.iccorp.shana.web.rest.util.HeaderUtil;
import fr.iccorp.shana.web.rest.vm.SettingsDTO;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsResource {
	
	private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

	private static final String ENTITY_NAME = "settings";
	
	@Autowired
	SettingsConfiguration settings;
    
	@GetMapping("/photo-couverture")
    public ResponseEntity<SettingsDTO> getPhotoCouverture() {
		log.debug("REST request to get photocouverture");
		SettingsDTO res = new SettingsDTO();
		res.setPhotoCouverture(settings.getPhotoCouverture());
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(res));
    }

	@PutMapping("/photo-couverture")
	public ResponseEntity<SettingsDTO> setPhotoCouverture(@RequestBody SettingsDTO settingsDTO) {
		log.debug("REST request to set photocouverture: {}", settingsDTO);
		settings.setPhotoCouverture(settingsDTO.getPhotoCouverture());
		SettingsDTO res = new SettingsDTO();
		res.setPhotoCouverture(settings.getPhotoCouverture());
		return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "photo de couverture"))
        .body(res);
	}
}
