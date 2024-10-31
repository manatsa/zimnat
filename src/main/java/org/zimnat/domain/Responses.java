package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Response entity.
 * @description - The Response entity holding response information
 * @author CodeMaster.
 */
@Schema(description = "Response entity.\n@description - The Response entity holding response information\n@author CodeMaster.")
@Entity
@Table(name = "responses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Responses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "result")
    private String result;

    @Column(name = "message")
    private String message;

    @JsonIgnoreProperties(value = { "response" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "response")
    private FailureResponse failureResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Responses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return this.result;
    }

    public Responses result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public Responses message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FailureResponse getFailureResponse() {
        return this.failureResponse;
    }

    public void setFailureResponse(FailureResponse failureResponse) {
        if (this.failureResponse != null) {
            this.failureResponse.setResponse(null);
        }
        if (failureResponse != null) {
            failureResponse.setResponse(this);
        }
        this.failureResponse = failureResponse;
    }

    public Responses failureResponse(FailureResponse failureResponse) {
        this.setFailureResponse(failureResponse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responses)) {
            return false;
        }
        return getId() != null && getId().equals(((Responses) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Responses{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
