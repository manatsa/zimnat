package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * LICResultResponse entity.
 * @description - The LICResultResponse entity holding licence confirmation response information
 * @author CodeMaster.
 */
@Schema(
    description = "LICResultResponse entity.\n@description - The LICResultResponse entity holding licence confirmation response information\n@author CodeMaster."
)
@Entity
@Table(name = "lic_result_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICResultResponse implements Serializable {

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

    @Column(name = "version")
    private String version;

    @JsonIgnoreProperties(value = { "lICResultResponse" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private LICConfirmationResponse response;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICResultResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerReference() {
        return this.partnerReference;
    }

    public LICResultResponse partnerReference(String partnerReference) {
        this.setPartnerReference(partnerReference);
        return this;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public String getTheDate() {
        return this.theDate;
    }

    public LICResultResponse theDate(String theDate) {
        this.setTheDate(theDate);
        return this;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getVersion() {
        return this.version;
    }

    public LICResultResponse version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LICConfirmationResponse getResponse() {
        return this.response;
    }

    public void setResponse(LICConfirmationResponse lICConfirmationResponse) {
        this.response = lICConfirmationResponse;
    }

    public LICResultResponse response(LICConfirmationResponse lICConfirmationResponse) {
        this.setResponse(lICConfirmationResponse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICResultResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((LICResultResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICResultResponse{" +
            "id=" + getId() +
            ", partnerReference='" + getPartnerReference() + "'" +
            ", theDate='" + getTheDate() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
