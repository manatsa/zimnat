package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * Vehicle entity.
 * @description - The Vehicle entity holding vehicle information
 * @author CodeMaster.
 */
@Schema(description = "Vehicle entity.\n@description - The Vehicle entity holding vehicle information\n@author CodeMaster.")
@Entity
@Table(name = "vehicle")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "v_rn", nullable = false)
    private String vRN;

    @NotNull
    @Column(name = "i_d_number", nullable = false)
    private String iDNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvRN() {
        return this.vRN;
    }

    public Vehicle vRN(String vRN) {
        this.setvRN(vRN);
        return this;
    }

    public void setvRN(String vRN) {
        this.vRN = vRN;
    }

    public String getiDNumber() {
        return this.iDNumber;
    }

    public Vehicle iDNumber(String iDNumber) {
        this.setiDNumber(iDNumber);
        return this;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return getId() != null && getId().equals(((Vehicle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", vRN='" + getvRN() + "'" +
            ", iDNumber='" + getiDNumber() + "'" +
            "}";
    }
}
