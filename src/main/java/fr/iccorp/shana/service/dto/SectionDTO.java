package fr.iccorp.shana.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Section entity.
 */
public class SectionDTO implements Serializable {

    private Long id;

    private String titre;

    private String textAvant;

    private String textApres;

    @Lob
    private byte[] photo;
    private String photoContentType;

    private Long articleId;

    private String articleNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTextAvant() {
        return textAvant;
    }

    public void setTextAvant(String textAvant) {
        this.textAvant = textAvant;
    }

    public String getTextApres() {
        return textApres;
    }

    public void setTextApres(String textApres) {
        this.textApres = textApres;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SectionDTO sectionDTO = (SectionDTO) o;
        if(sectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectionDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", textAvant='" + getTextAvant() + "'" +
            ", textApres='" + getTextApres() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
