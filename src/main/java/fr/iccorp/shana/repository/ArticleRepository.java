package fr.iccorp.shana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.domain.Categorie;
import fr.iccorp.shana.service.dto.ArticleDTO;


/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByCategorie(Categorie categorie);
}
