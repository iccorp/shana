package fr.iccorp.shana.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Abonne entity.
 */
public class AbonneDTO implements Serializable {

    private Long id;

    @NotNull
    private String pseudo;

    @NotNull
    private String email;

    @NotNull
    private String motDePasse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbonneDTO abonneDTO = (AbonneDTO) o;
        if(abonneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), abonneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AbonneDTO{" +
            "id=" + getId() +
            ", pseudo='" + getPseudo() + "'" +
            ", email='" + getEmail() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            "}";
    }
}
