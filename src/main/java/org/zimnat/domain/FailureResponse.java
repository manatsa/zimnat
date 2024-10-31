package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * FailureResponse entity.
 * @description - The Request entity holding request failure response information
 * @author CodeMaster.
 */
@Schema(
    description = "FailureResponse entity.\n@description - The Request entity holding request failure response information\n@author CodeMaster."
)
@Entity
@Table(name = "failure_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FailureResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "partner_reference")
    private String partnerReference;

    @Column(name = "date")
    private String date;

    @Column(name = "version")
    private String version;

    @JsonIgnoreProperties(value = { "failureResponse" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Responses response;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FailureResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public FailureResponse partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getDate() {
        return this.date;
    }

    public FailureResponse date(String date) {
        this.setDate(date);
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return this.version;
    }

    public FailureResponse version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Responses getResponse() {
        return this.response;
    }

    public void setResponse(Responses responses) {
        this.response = responses;
    }

    public FailureResponse response(Responses responses) {
        this.setResponse(responses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FailureResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((FailureResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FailureResponse{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", date='" + getDate() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
