package fr.iccorp.shana.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "text_avant")
    private String textAvant;

    @Column(name = "text_apres")
    private String textApres;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Section titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTextAvant() {
        return textAvant;
    }

    public Section textAvant(String textAvant) {
        this.textAvant = textAvant;
        return this;
    }

    public void setTextAvant(String textAvant) {
        this.textAvant = textAvant;
    }

    public String getTextApres() {
        return textApres;
    }

    public Section textApres(String textApres) {
        this.textApres = textApres;
        return this;
    }

    public void setTextApres(String textApres) {
        this.textApres = textApres;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Section photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Section photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Article getArticle() {
        return article;
    }

    public Section article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        if (section.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", textAvant='" + getTextAvant() + "'" +
            ", textApres='" + getTextApres() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
