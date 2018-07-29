package fr.iccorp.shana.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Article entity.
 */
public class ArticleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String idPhoto;

    @Lob
    private byte[] photo;
    private String photoContentType;

    @NotNull
    private String titre;

    private Integer position;

    private Integer positionDansCategorie;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateDerniereModification;

    private Integer nbVue;

    private Integer nbLike;

    private Integer nbPartage;

    private Long categorieId;

    private String categorieNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPositionDansCategorie() {
        return positionDansCategorie;
    }

    public void setPositionDansCategorie(Integer positionDansCategorie) {
        this.positionDansCategorie = positionDansCategorie;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(ZonedDateTime dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public Integer getNbVue() {
        return nbVue;
    }

    public void setNbVue(Integer nbVue) {
        this.nbVue = nbVue;
    }

    public Integer getNbLike() {
        return nbLike;
    }

    public void setNbLike(Integer nbLike) {
        this.nbLike = nbLike;
    }

    public Integer getNbPartage() {
        return nbPartage;
    }

    public void setNbPartage(Integer nbPartage) {
        this.nbPartage = nbPartage;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public void setCategorieNom(String categorieNom) {
        this.categorieNom = categorieNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if(articleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", idPhoto='" + getIdPhoto() + "'" +
            ", photo='" + getPhoto() + "'" +
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
