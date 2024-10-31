package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * BatchStatusRequest entity.
 * @description - The Request entity holding Batch Status request information
 * @author CodeMaster.
 */
@Schema(
    description = "BatchStatusRequest entity.\n@description - The Request entity holding Batch Status request information\n@author CodeMaster."
)
@Entity
@Table(name = "batch_status_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchStatusRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "the_function", nullable = false)
    private String theFunction;

    @NotNull
    @Column(name = "insurance_id_batch", nullable = false)
    private String insuranceIDBatch;

    @JsonIgnoreProperties(value = { "request" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "request")
    private BatchStatus batchStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BatchStatusRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheFunction() {
        return this.theFunction;
    }

    public BatchStatusRequest theFunction(String theFunction) {
        this.setTheFunction(theFunction);
        return this;
    }

    public void setTheFunction(String theFunction) {
        this.theFunction = theFunction;
    }

    public String getInsuranceIDBatch() {
        return this.insuranceIDBatch;
    }

    public BatchStatusRequest insuranceIDBatch(String insuranceIDBatch) {
        this.setInsuranceIDBatch(insuranceIDBatch);
        return this;
    }

    public void setInsuranceIDBatch(String insuranceIDBatch) {
        this.insuranceIDBatch = insuranceIDBatch;
    }

    public BatchStatus getBatchStatus() {
        return this.batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        if (this.batchStatus != null) {
            this.batchStatus.setRequest(null);
        }
        if (batchStatus != null) {
            batchStatus.setRequest(this);
        }
        this.batchStatus = batchStatus;
    }

    public BatchStatusRequest batchStatus(BatchStatus batchStatus) {
        this.setBatchStatus(batchStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchStatusRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((BatchStatusRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchStatusRequest{" +
            "id=" + getId() +
            ", theFunction='" + getTheFunction() + "'" +
            ", insuranceIDBatch='" + getInsuranceIDBatch() + "'" +
            "}";
    }
}
