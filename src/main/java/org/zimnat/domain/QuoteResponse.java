package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * QuoteResponse entity.
 * @description - The QuoteResponse entity holding licence Quote response information
 * @author CodeMaster.
 */
@Schema(
    description = "QuoteResponse entity.\n@description - The QuoteResponse entity holding licence Quote response information\n@author CodeMaster."
)
@Entity
@Table(name = "quote_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuoteResponse implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Quotes quotes;

    @JsonIgnoreProperties(value = { "response" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "response")
    private LICQuoteResponse lICQuoteResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuoteResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return this.result;
    }

    public QuoteResponse result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public QuoteResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Quotes getQuotes() {
        return this.quotes;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    public QuoteResponse quotes(Quotes quotes) {
        this.setQuotes(quotes);
        return this;
    }

    public LICQuoteResponse getLICQuoteResponse() {
        return this.lICQuoteResponse;
    }

    public void setLICQuoteResponse(LICQuoteResponse lICQuoteResponse) {
        if (this.lICQuoteResponse != null) {
            this.lICQuoteResponse.setResponse(null);
        }
        if (lICQuoteResponse != null) {
            lICQuoteResponse.setResponse(this);
        }
        this.lICQuoteResponse = lICQuoteResponse;
    }

    public QuoteResponse lICQuoteResponse(LICQuoteResponse lICQuoteResponse) {
        this.setLICQuoteResponse(lICQuoteResponse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((QuoteResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteResponse{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
