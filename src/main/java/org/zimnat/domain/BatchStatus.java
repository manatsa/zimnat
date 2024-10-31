package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * BatchStatus entity.
 * @description - The BatchStatus entity holding batch status request parameters
 * @author CodeMaster.
 */
@Schema(
    description = "BatchStatus entity.\n@description - The BatchStatus entity holding batch status request parameters\n@author CodeMaster."
)
@Entity
@Table(name = "batch_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchStatus implements Serializable {

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

    @JsonIgnoreProperties(value = { "batchStatus" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private BatchStatusRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BatchStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public BatchStatus partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public BatchStatus theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTheVersion() {
        return this.theVersion;
    }

    public BatchStatus theVersion(String theVersion) {
        this.setTheVersion(theVersion);
        return this;
    }

    public void setTheVersion(String theVersion) {
        this.theVersion = theVersion;
    }

    public String getPartnerToken() {
        return this.partnerToken;
    }

    public BatchStatus partnerToken(String partnerToken) {
        this.setPartnerToken(partnerToken);
        return this;
    }

    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public BatchStatusRequest getRequest() {
        return this.request;
    }

    public void setRequest(BatchStatusRequest batchStatusRequest) {
        this.request = batchStatusRequest;
    }

    public BatchStatus request(BatchStatusRequest batchStatusRequest) {
        this.setRequest(batchStatusRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchStatus)) {
            return false;
        }
        return getId() != null && getId().equals(((BatchStatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchStatus{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", theVersion='" + getTheVersion() + "'" +
            ", partnerToken='" + getPartnerToken() + "'" +
            "}";
    }
}
