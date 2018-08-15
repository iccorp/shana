package fr.iccorp.shana.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.iccorp.shana.ShanaApp;
import fr.iccorp.shana.domain.Article;
import fr.iccorp.shana.service.dto.ArticleDTO;
import fr.iccorp.shana.service.mapper.ArticleMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShanaApp.class)
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void saveTest() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setNom("iccorp");
        articleDTO.setIdPhoto("idPhoto");
        Article article = articleMapper.toEntity(articleDTO);
        assertThat(article.getNom()).isEqualTo("iccorp");
        assertThat(article.getIdPhoto()).isEqualTo("idPhoto");
    }
}
