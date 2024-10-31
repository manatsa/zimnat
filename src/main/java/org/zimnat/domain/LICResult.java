package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * LICResult entity.
 * @description - The LICResult entity licence confirmation request parameters
 * @author CodeMaster.
 */
@Schema(description = "LICResult entity.\n@description - The LICResult entity licence confirmation request parameters\n@author CodeMaster.")
@Entity
@Table(name = "lic_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "partner_reference", nullable = false)
    private String partnerReference;

    @NotNull
    @Column(name = "the_date", nullable = false)
    private String theDate;

    @NotNull
    @Column(name = "the_version", nullable = false)
    private String theVersion;

    @NotNull
    @Column(name = "partner_token", nullable = false)
    private String partnerToken;

    @NotNull
    @Column(name = "licence_id", nullable = false)
    private String licenceID;

    @NotNull
    @Column(name = "jhi_function", nullable = false)
    private String function;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public LICResult partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public LICResult theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public LICResult theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public String getPartnerToken() {
        return this.partnerToken;
    }

    public LICResult partnerToken(String partnerToken) {
        this.setPartnerToken(partnerToken);
        return this;
    }

    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public String getLicenceID() {
        return this.licenceID;
    }

    public LICResult licenceID(String licenceID) {
        this.setLicenceID(licenceID);
        return this;
    }

    public void setLicenceID(String licenceID) {
        this.licenceID = licenceID;
    }

    public String getFunction() {
        return this.function;
    }

    public LICResult function(String function) {
        this.setFunction(function);
        return this;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICResult)) {
            return false;
        }
        return getId() != null && getId().equals(((LICResult) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICResult{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            ", partnerToken='" + getPartnerToken() + "'" +
            ", licenceID='" + getLicenceID() + "'" +
            ", function='" + getFunction() + "'" +
            "}";
    }
}
