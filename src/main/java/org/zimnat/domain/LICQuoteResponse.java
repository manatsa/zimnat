package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * LICQuoteResponse entity.
 * @description - The LICQuoteResponse entity holding Licence quote response information
 * @author CodeMaster.
 */
@Schema(
    description = "LICQuoteResponse entity.\n@description - The LICQuoteResponse entity holding Licence quote response information\n@author CodeMaster."
)
@Entity
@Table(name = "lic_quote_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICQuoteResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "partner_reference")
    private String partnerReference;

    @Column(name = "the_date")
    private String theDate;

    @Column(name = "the_version")
    private String theVersion;

    @JsonIgnoreProperties(value = { "quotes", "lICQuoteResponse" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private QuoteResponse response;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICQuoteResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public LICQuoteResponse partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public LICQuoteResponse theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public LICQuoteResponse theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public QuoteResponse getResponse() {
        return this.response;
    }

    public void setResponse(QuoteResponse quoteResponse) {
        this.response = quoteResponse;
    }

    public LICQuoteResponse response(QuoteResponse quoteResponse) {
        this.setResponse(quoteResponse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICQuoteResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((LICQuoteResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICQuoteResponse{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            "}";
    }
}
