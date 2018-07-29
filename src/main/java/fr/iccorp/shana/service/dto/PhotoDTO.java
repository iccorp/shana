package fr.iccorp.shana.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;

/**
 * A DTO for the Photo entity.
 */
public class PhotoDTO implements Serializable {

    private Long id;

    private String idPhoto;

    private FORMAT_PHOTO format;

    @Lob
    private byte[] photo;
    private String photoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public FORMAT_PHOTO getFormat() {
        return format;
    }

    public void setFormat(FORMAT_PHOTO format) {
        this.format = format;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotoDTO photoDTO = (PhotoDTO) o;
        if(photoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhotoDTO{" +
            "id=" + getId() +
            ", idPhoto='" + getIdPhoto() + "'" +
            ", format='" + getFormat() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
