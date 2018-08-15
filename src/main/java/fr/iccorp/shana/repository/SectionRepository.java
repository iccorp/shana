package fr.iccorp.shana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.iccorp.shana.domain.Section;

import java.util.List;

/**
 * Spring Data JPA repository for the Section entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    public List<Section> findByArticleId(Long id);
}
