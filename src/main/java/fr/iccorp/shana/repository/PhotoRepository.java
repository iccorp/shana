package fr.iccorp.shana.repository;

import fr.iccorp.shana.domain.Photo;
import fr.iccorp.shana.service.dto.PhotoDTO;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
	public PhotoDTO findByIdPhoto(String idPhoto);
}
