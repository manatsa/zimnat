package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * TPILICQuote entity.
 * @description - The TPILICQuote entity licence and insurance request parameters
 * @author CodeMaster.
 */
@Schema(
    description = "TPILICQuote entity.\n@description - The TPILICQuote entity licence and insurance request parameters\n@author CodeMaster."
)
@Entity
@Table(name = "tpilic_quote")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TPILICQuote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "partner_reference", nullable = false)
    private UUID partnerReference;

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
    @Column(name = "jhi_function", nullable = false)
    private String function;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TPILICQuote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPartnerReference() {
        return this.partnerReference;
    }

    public TPILICQuote partnerReference(UUID partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(UUID partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public TPILICQuote theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public TPILICQuote theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public String getPartnerToken() {
        return this.partnerToken;
    }

    public TPILICQuote partnerToken(String partnerToken) {
        this.setPartnerToken(partnerToken);
        return this;
    }

    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public String getFunction() {
        return this.function;
    }

    public TPILICQuote function(String function) {
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
        if (!(o instanceof TPILICQuote)) {
            return false;
        }
        return getId() != null && getId().equals(((TPILICQuote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPILICQuote{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            ", partnerToken='" + getPartnerToken() + "'" +
            ", function='" + getFunction() + "'" +
            "}";
    }
}
