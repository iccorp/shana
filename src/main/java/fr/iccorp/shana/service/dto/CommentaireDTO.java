package fr.iccorp.shana.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Commentaire entity.
 */
public class CommentaireDTO implements Serializable {

    private Long id;

    private String contenu;

    private Long articleId;

    private String articleNom;

    private Long abonneId;

    private String abonnePseudo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleNom() {
        return articleNom;
    }

    public void setArticleNom(String articleNom) {
        this.articleNom = articleNom;
    }

    public Long getAbonneId() {
        return abonneId;
    }

    public void setAbonneId(Long abonneId) {
        this.abonneId = abonneId;
    }

    public String getAbonnePseudo() {
        return abonnePseudo;
    }

    public void setAbonnePseudo(String abonnePseudo) {
        this.abonnePseudo = abonnePseudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentaireDTO commentaireDTO = (CommentaireDTO) o;
        if(commentaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentaireDTO{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            "}";
    }
}
