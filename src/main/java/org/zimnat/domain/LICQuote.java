package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * LICQuote entity.
 * @description - The LICQuote entity holding Licence quote request parameters
 * @author CodeMaster.
 */
@Schema(description = "LICQuote entity.\n@description - The LICQuote entity holding Licence quote request parameters\n@author CodeMaster.")
@Entity
@Table(name = "lic_quote")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICQuote implements Serializable {

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

    @JsonIgnoreProperties(value = { "vehicles", "lICQuote" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private LICQuoteRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICQuote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public LICQuote partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public LICQuote theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public LICQuote theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public String getPartnerToken() {
        return this.partnerToken;
    }

    public LICQuote partnerToken(String partnerToken) {
        this.setPartnerToken(partnerToken);
        return this;
    }

    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public LICQuoteRequest getRequest() {
        return this.request;
    }

    public void setRequest(LICQuoteRequest lICQuoteRequest) {
        this.request = lICQuoteRequest;
    }

    public LICQuote request(LICQuoteRequest lICQuoteRequest) {
        this.setRequest(lICQuoteRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICQuote)) {
            return false;
        }
        return getId() != null && getId().equals(((LICQuote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICQuote{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            ", partnerToken='" + getPartnerToken() + "'" +
            "}";
    }
}
