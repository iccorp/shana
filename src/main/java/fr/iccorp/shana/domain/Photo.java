package fr.iccorp.shana.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_photo")
    private String idPhoto;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private FORMAT_PHOTO format;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public Photo idPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
        return this;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public FORMAT_PHOTO getFormat() {
        return format;
    }

    public Photo format(FORMAT_PHOTO format) {
        this.format = format;
        return this;
    }

    public void setFormat(FORMAT_PHOTO format) {
        this.format = format;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Photo photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Photo photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
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
        Photo photo = (Photo) o;
        if (photo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", idPhoto='" + getIdPhoto() + "'" +
            ", format='" + getFormat() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
