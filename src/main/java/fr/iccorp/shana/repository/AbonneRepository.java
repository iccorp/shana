package fr.iccorp.shana.repository;

import fr.iccorp.shana.domain.Abonne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Abonne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Long> {

}
