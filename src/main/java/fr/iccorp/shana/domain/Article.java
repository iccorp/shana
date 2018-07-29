package fr.iccorp.shana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "id_photo")
    private String idPhoto;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "position")
    private Integer position;

    @Column(name = "position_dans_categorie")
    private Integer positionDansCategorie;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Column(name = "date_derniere_modification")
    private ZonedDateTime dateDerniereModification;

    @Column(name = "nb_vue")
    private Integer nbVue;

    @Column(name = "nb_like")
    private Integer nbLike;

    @Column(name = "nb_partage")
    private Integer nbPartage;

    @ManyToOne
    private Categorie categorie;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private Set<Section> sections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Article nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public Article idPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
        return this;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Article photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Article photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getTitre() {
        return titre;
    }

    public Article titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getPosition() {
        return position;
    }

    public Article position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPositionDansCategorie() {
        return positionDansCategorie;
    }

    public Article positionDansCategorie(Integer positionDansCategorie) {
        this.positionDansCategorie = positionDansCategorie;
        return this;
    }

    public void setPositionDansCategorie(Integer positionDansCategorie) {
        this.positionDansCategorie = positionDansCategorie;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public Article dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }

    public Article dateDerniereModification(ZonedDateTime dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
        return this;
    }

    public void setDateDerniereModification(ZonedDateTime dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public Integer getNbVue() {
        return nbVue;
    }

    public Article nbVue(Integer nbVue) {
        this.nbVue = nbVue;
        return this;
    }

    public void setNbVue(Integer nbVue) {
        this.nbVue = nbVue;
    }

    public Integer getNbLike() {
        return nbLike;
    }

    public Article nbLike(Integer nbLike) {
        this.nbLike = nbLike;
        return this;
    }

    public void setNbLike(Integer nbLike) {
        this.nbLike = nbLike;
    }

    public Integer getNbPartage() {
        return nbPartage;
    }

    public Article nbPartage(Integer nbPartage) {
        this.nbPartage = nbPartage;
        return this;
    }

    public void setNbPartage(Integer nbPartage) {
        this.nbPartage = nbPartage;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Article categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public Article sections(Set<Section> sections) {
        this.sections = sections;
        return this;
    }

    public Article addSection(Section section) {
        this.sections.add(section);
        section.setArticle(this);
        return this;
    }

    public Article removeSection(Section section) {
        this.sections.remove(section);
        section.setArticle(null);
        return this;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
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
        Article article = (Article) o;
        if (article.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", idPhoto='" + getIdPhoto() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", titre='" + getTitre() + "'" +
            ", position=" + getPosition() +
            ", positionDansCategorie=" + getPositionDansCategorie() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateDerniereModification='" + getDateDerniereModification() + "'" +
            ", nbVue=" + getNbVue() +
            ", nbLike=" + getNbLike() +
            ", nbPartage=" + getNbPartage() +
            "}";
    }
}
