package org.zimnat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * Product entity.
 * @description - The Product entity holding product information
 * @author CodeMaster.
 */
@Schema(description = "Product entity.\n@description - The Product entity holding product information\n@author CodeMaster.")
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Column(name = "risk_code", nullable = false)
    private String riskCode;

    @NotNull
    @Column(name = "screen_code", nullable = false)
    private String screenCode;

    @NotNull
    @Column(name = "data_model_code", nullable = false)
    private String dataModelCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRiskCode() {
        return this.riskCode;
    }

    public Product riskCode(String riskCode) {
        this.setRiskCode(riskCode);
        return this;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getScreenCode() {
        return this.screenCode;
    }

    public Product screenCode(String screenCode) {
        this.setScreenCode(screenCode);
        return this;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getDataModelCode() {
        return this.dataModelCode;
    }

    public Product dataModelCode(String dataModelCode) {
        this.setDataModelCode(dataModelCode);
        return this;
    }

    public void setDataModelCode(String dataModelCode) {
        this.dataModelCode = dataModelCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            ", riskCode='" + getRiskCode() + "'" +
            ", screenCode='" + getScreenCode() + "'" +
            ", dataModelCode='" + getDataModelCode() + "'" +
            "}";
    }
}
