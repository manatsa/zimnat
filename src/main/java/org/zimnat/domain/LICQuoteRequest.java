package org.zimnat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * LICQuoteRequest entity.
 * @description - The LICQuoteRequest entity holding LICQuote request information
 * @author CodeMaster.
 */
@Schema(
    description = "LICQuoteRequest entity.\n@description - The LICQuoteRequest entity holding LICQuote request information\n@author CodeMaster."
)
@Entity
@Table(name = "lic_quote_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LICQuoteRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "the_function", nullable = false)
    private String theFunction;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicles;

    @JsonIgnoreProperties(value = { "request" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "request")
    private LICQuote lICQuote;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LICQuoteRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheFunction() {
        return this.theFunction;
    }

    public LICQuoteRequest theFunction(String theFunction) {
        this.setTheFunction(theFunction);
        return this;
    }

    public void setTheFunction(String theFunction) {
        this.theFunction = theFunction;
    }

    public Vehicle getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(Vehicle vehicle) {
        this.vehicles = vehicle;
    }

    public LICQuoteRequest vehicles(Vehicle vehicle) {
        this.setVehicles(vehicle);
        return this;
    }

    public LICQuote getLICQuote() {
        return this.lICQuote;
    }

    public void setLICQuote(LICQuote lICQuote) {
        if (this.lICQuote != null) {
            this.lICQuote.setRequest(null);
        }
        if (lICQuote != null) {
            lICQuote.setRequest(this);
        }
        this.lICQuote = lICQuote;
    }

    public LICQuoteRequest lICQuote(LICQuote lICQuote) {
        this.setLICQuote(lICQuote);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LICQuoteRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((LICQuoteRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LICQuoteRequest{" +
            "id=" + getId() +
            ", theFunction='" + getTheFunction() + "'" +
            "}";
    }
}
